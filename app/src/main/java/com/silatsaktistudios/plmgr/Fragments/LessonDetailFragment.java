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

import com.silatsaktistudios.plmgr.Models.Lesson;
import com.silatsaktistudios.plmgr.R;

import android.text.format.DateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LessonDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LessonDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LessonDetailFragment extends Fragment {

    private boolean viewLesson = true;

    private OnFragmentInteractionListener mListener;

    private Lesson lesson;
    private Button editButton, saveButton, deleteButton;
    private Space space1,space2,space3;
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
    public static LessonDetailFragment newInstance(Lesson lesson, boolean viewLesson) {
        LessonDetailFragment fragment = new LessonDetailFragment();
        fragment.lesson = lesson;
        fragment.viewLesson = viewLesson;
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
                loadEditData();
                showLessonEditViews();
            }
        });

        saveButton = (Button)v.findViewById(R.id.lessonSaveButton);
        deleteButton = (Button)v.findViewById(R.id.lessonDeleteButton);

        space1 = (Space)v.findViewById(R.id.buttonSpace1);
        space2 = (Space)v.findViewById(R.id.buttonSpace2);
        space3 = (Space)v.findViewById(R.id.buttonSpace3);

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

        if(viewLesson) {
            loadViewData();
        }
        else {
            showLessonEditViews();
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

    private void showLessonEditViews() {
        editButton.setVisibility(View.GONE);
        saveButton.setVisibility(View.VISIBLE);

        if(!viewLesson) {
            deleteButton.setVisibility(View.GONE);
            space1.setVisibility(View.GONE);
            space2.setVisibility(View.GONE);
            space3.setVisibility(View.GONE);
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

    private int getYearFrom(Date date) {
        return Integer.parseInt(DateFormat.format("yyyy", date).toString());
    }

    private int getMonthFrom(Date date) {
        return Integer.parseInt(DateFormat.format("MM", date).toString()) - 1;
    }

    private int getDayFrom(Date date) {
        return Integer.parseInt(DateFormat.format("dd", date).toString());
    }

    private int getHourFrom(Date date) {
        return Integer.parseInt(DateFormat.format("HH", date).toString());
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
