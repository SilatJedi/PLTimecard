package com.silatsaktistudios.plmgr.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.Space;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.silatsaktistudios.plmgr.DataLogic.LessonData;
import com.silatsaktistudios.plmgr.DataLogic.StudentData;
import com.silatsaktistudios.plmgr.Models.Lesson;
import com.silatsaktistudios.plmgr.R;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LessonDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LessonDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LessonDetailFragment extends Fragment {


    private boolean isViewingLesson = true;
    private boolean isEditing = false;
    private int lessonID = -1;

    private OnFragmentInteractionListener mListener;

    private Lesson lesson;
    private Button editButton, saveButton, deleteButton;
    private Space space1,space2,space3,space4;
    private TextView studentNameView, studentNameEdit, lessonDateView, lessonTimeView,
            lessonGradeView, lessonGradeEdit, lessonViewNotes;
    private EditText lessonEditNotes;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private CheckBox showedUpCheckBox, makeUpCheckBox, eligibleCheckBox;

    public LessonDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LessonDetailFragment.
     */
    public static LessonDetailFragment newInstance(int lessonID, boolean viewLesson) {
        LessonDetailFragment fragment = new LessonDetailFragment();
        fragment.lessonID = lessonID;
        Realm realm = Realm.getDefaultInstance();
        fragment.lesson = realm.where(Lesson.class).equalTo("id", lessonID).findFirst();
        fragment.isViewingLesson = viewLesson;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lesson_detail, container, false);

        editButton = (Button)v.findViewById(R.id.lessonEditButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isEditing = true;
                loadEditData();
                showLessonEdit();
            }
        });

        saveButton = (Button)v.findViewById(R.id.lessonSaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLessonData();
                loadViewData();
                showLessonView();
                isEditing = false;
            }
        });

        deleteButton = (Button)v.findViewById(R.id.lessonDeleteButton);

        space1 = (Space)v.findViewById(R.id.buttonSpace1);
        space2 = (Space)v.findViewById(R.id.buttonSpace2);
        space3 = (Space)v.findViewById(R.id.buttonSpace3);
        space4 = (Space)v.findViewById(R.id.buttonSpace4);

        studentNameView = (TextView)v.findViewById(R.id.lessonViewStudentName);
        studentNameEdit = (TextView)v.findViewById(R.id.lessonEditStudentName);
        lessonDateView = (TextView)v.findViewById(R.id.lessonViewDate);
        lessonTimeView = (TextView)v.findViewById(R.id.lessonViewTime);
        lessonGradeView = (TextView)v.findViewById(R.id.lessonViewGrade);
        lessonGradeEdit = (TextView)v.findViewById(R.id.lessonEditGrade);
        lessonViewNotes = (TextView)v.findViewById(R.id.lessonViewNotes);

        lessonEditNotes = (EditText)v.findViewById(R.id.lessonEditNotes);

        datePicker = (DatePicker)v.findViewById(R.id.lessonDatePicker);
        timePicker = (TimePicker)v.findViewById(R.id.lessonTimePicker);

        showedUpCheckBox = (CheckBox)v.findViewById(R.id.lessonViewShowedUpCheckBox);
        makeUpCheckBox = (CheckBox)v.findViewById(R.id.lessonViewMakeUpCheckBox);
        eligibleCheckBox = (CheckBox)v.findViewById(R.id.lessonViewEligibleCheckBox);

        if(isViewingLesson) {
            loadViewData();
        }
        else {
            showLessonEdit();
        }

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }





    private void saveLessonData() {
        String grade = lessonGradeEdit.getText().toString();
        String notes = lessonEditNotes.getText().toString();

        if(isEditing) {
            lesson = new Lesson(
                    lessonID,
                    lesson.getStudentID(),
                    lesson.getStudentName(),
                    getDateTime(),
                    convertGrade(grade),
                    notes,
                    showedUpCheckBox.isChecked(),
                    eligibleCheckBox.isChecked(),
                    makeUpCheckBox.isChecked()
            );
            LessonData.edit(lesson);
        }
        else {
            String name = studentNameEdit.getText().toString();
            lesson = new Lesson(
                    StudentData.getIdForStudent(name),
                    name,
                    getDateTime(),
                    convertGrade(grade),
                    notes,
                    showedUpCheckBox.isChecked(),
                    eligibleCheckBox.isChecked(),
                    makeUpCheckBox.isChecked());
            LessonData.add(lesson);
        }

    }







    private void loadViewData() {
        studentNameView.setText(lesson.getStudentName());
        lessonDateView.setText(DateFormat.format("MMMM dd, yyyy",lesson.getDate()));
        lessonTimeView.setText(DateFormat.format("hh:mm a",lesson.getDate()));
        showedUpCheckBox.setChecked(lesson.didShowUp());
        makeUpCheckBox.setChecked(lesson.isMakeUp());
        eligibleCheckBox.setChecked(lesson.isEligible());
        lessonGradeView.setText(convertGrade(lesson.getGrade()));
        lessonViewNotes.setText(lesson.getNote());
    }

    private void loadEditData() {
        studentNameEdit.setText(lesson.getStudentName());

        datePicker.updateDate(
                getYearFrom(lesson.getDate()),
                getMonthFrom(lesson.getDate()),
                getDayFrom(lesson.getDate()));

        if(Build.VERSION.SDK_INT < 23) {
            timePicker.setCurrentHour(getHourFrom(lesson.getDate()));
            timePicker.setCurrentMinute(getMinuteFrom(lesson.getDate()));
        }
        else {
            timePicker.setHour(getHourFrom(lesson.getDate()));
            timePicker.setMinute(getMinuteFrom(lesson.getDate()));
        }

        lessonGradeEdit.setText(convertGrade(lesson.getGrade()));
        lessonEditNotes.setText(lesson.getNote());
    }













    private void showLessonView() {
        editButton.setVisibility(View.VISIBLE);
        saveButton.setVisibility(View.GONE);

        if(!isViewingLesson) {
            deleteButton.setVisibility(View.VISIBLE);
            space1.setVisibility(View.VISIBLE);
            space2.setVisibility(View.VISIBLE);
            space3.setVisibility(View.VISIBLE);
            space4.setVisibility(View.VISIBLE);
        }

        studentNameView.setVisibility(View.VISIBLE);
        studentNameEdit.setVisibility(View.GONE);

        lessonDateView.setVisibility(View.VISIBLE);
        datePicker.setVisibility(View.GONE);

        lessonTimeView.setVisibility(View.VISIBLE);
        timePicker.setVisibility(View.GONE);

        showedUpCheckBox.setClickable(false);
        makeUpCheckBox.setClickable(false);
        eligibleCheckBox.setClickable(false);

        lessonGradeView.setVisibility(View.VISIBLE);
        lessonGradeEdit.setVisibility(View.GONE);

        lessonViewNotes.setVisibility(View.VISIBLE);
        lessonEditNotes.setVisibility(View.GONE);
    }

    private void showLessonEdit() {
        editButton.setVisibility(View.GONE);
        saveButton.setVisibility(View.VISIBLE);

        if(!isViewingLesson) {
            deleteButton.setVisibility(View.GONE);
            space1.setVisibility(View.GONE);
            space2.setVisibility(View.GONE);
            space3.setVisibility(View.GONE);
            space4.setVisibility(View.GONE);
        }

        studentNameView.setVisibility(View.GONE);
        studentNameEdit.setVisibility(View.VISIBLE);

        lessonDateView.setVisibility(View.GONE);
        datePicker.setVisibility(View.VISIBLE);

        lessonTimeView.setVisibility(View.GONE);
        timePicker.setVisibility(View.VISIBLE);

        showedUpCheckBox.setClickable(true);
        makeUpCheckBox.setClickable(true);
        eligibleCheckBox.setClickable(true);

        lessonGradeView.setVisibility(View.GONE);
        lessonGradeEdit.setVisibility(View.VISIBLE);

        lessonViewNotes.setVisibility(View.GONE);
        lessonEditNotes.setVisibility(View.VISIBLE);
    }











    private Date getDateTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
        calendar.set(Calendar.MONTH, datePicker.getMonth());
        Log.d("datePicker.getYear()", String.valueOf(datePicker.getYear()));
        calendar.set(Calendar.YEAR, datePicker.getYear());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
            Log.d("timePicker.getHour()", String.valueOf(timePicker.getHour()));
            Log.d("timePicker.getMinute()", String.valueOf(timePicker.getMinute()));

            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
            calendar.set(Calendar.MINUTE, timePicker.getMinute());
        } else {
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
        }

        calendar.set(Calendar.SECOND, 0);

        Log.d("calendar.getTime()", calendar.getTime().toString());


        return calendar.getTime();
    }

    private int getYearFrom(Date date) {
        return Integer.parseInt(DateFormat.format("yyyy", date).toString());
    }

    private int getMonthFrom(Date date) {
        return Integer.parseInt(DateFormat.format("M", date).toString()) - 1;
    }

    private int getDayFrom(Date date) {
        return Integer.parseInt(DateFormat.format("d", date).toString());
    }

    private int getHourFrom(Date date) {
        return Integer.parseInt(DateFormat.format("H", date).toString());
    }

    private int getMinuteFrom(Date date) {
        return Integer.parseInt(DateFormat.format("mm", date).toString());
    }

    private String convertGrade(float grade) {
        String sGrade = String.valueOf(grade);

        Log.d("grade", sGrade);

        switch(sGrade) {
            case "0.0": return "F";
            case "0.67": return "D-";
            case "1.0": return "D";
            case "1.33": return "D+";
            case "1.67": return "C-";
            case "2.0": return "C";
            case "2.33": return "C+";
            case "2.67": return "B-";
            case "3.0": return "B";
            case "3.33": return "B+";
            case "3.67": return "A-";
            case "4.0": return "A";
            default: return "F";
        }
    }

    private float convertGrade(String grade) {
        switch(grade) {
            case "F" : return 0f;
            case "D-" : return 0.67f;
            case "D" : return 1f;
            case "D+" : return 1.33f;
            case "C-" : return 1.67f;
            case "C" : return 2f;
            case "C+" : return 2.33f;
            case "B-" : return 2.67f;
            case "B" : return 3f;
            case "B+" : return 3.33f;
            case "A-" : return 3.67f;
            case "A" : return 4f;
            default : return -1;
        }
    }
}
