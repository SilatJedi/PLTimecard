package com.silatsaktistudios.plmgr.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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




public class LessonDetailFragment extends Fragment {


    private boolean isNewLesson = false;
    private boolean isEditing = false;
    public int lessonID = -1;


    private Lesson lesson;
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
    public static LessonDetailFragment newInstance(int lessonID) {
        LessonDetailFragment fragment = new LessonDetailFragment();
        fragment.lessonID = lessonID;
        fragment.lesson = LessonData.getLesson(lessonID);
        return fragment;
    }

    public static LessonDetailFragment newInstance() {
        LessonDetailFragment fragment = new LessonDetailFragment();
        fragment.isNewLesson = true;
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

        studentNameView = (TextView)v.findViewById(R.id.lessonViewStudentName);
        studentNameEdit = (TextView)v.findViewById(R.id.lessonEditStudentName);
        studentNameEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] names = StudentData.studentNameArray();

                new AlertDialog.Builder(getActivity())
                        .setTitle("Select Student")
                        .setSingleChoiceItems(names, -1,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int selection) {
                                        studentNameEdit.setText(names[selection]);
                                        dialogInterface.dismiss();
                                    }
                                }
                        )
                        .setNegativeButton("Cancel", null)
                .show();
            }
        });

        lessonDateView = (TextView)v.findViewById(R.id.lessonViewDate);
        lessonTimeView = (TextView)v.findViewById(R.id.lessonViewTime);
        lessonGradeView = (TextView)v.findViewById(R.id.lessonViewGrade);

        lessonGradeEdit = (TextView)v.findViewById(R.id.lessonEditGrade);
        lessonGradeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] grades = getResources().getStringArray(R.array.grades);

                new AlertDialog.Builder(getActivity())
                        .setTitle("Select Grade")
                        .setSingleChoiceItems(grades, -1, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int selection) {
                                lessonGradeEdit.setText(grades[selection]);
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                .show();
            }
        });

        lessonViewNotes = (TextView)v.findViewById(R.id.lessonViewNotes);

        lessonEditNotes = (EditText)v.findViewById(R.id.lessonEditNotes);

        datePicker = (DatePicker)v.findViewById(R.id.lessonDatePicker);
        timePicker = (TimePicker)v.findViewById(R.id.lessonTimePicker);

        showedUpCheckBox = (CheckBox)v.findViewById(R.id.lessonViewShowedUpCheckBox);
        showedUpCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(showedUpCheckBox.isChecked()) {
                    eligibleCheckBox.setVisibility(View.GONE);
                } else {
                    eligibleCheckBox.setVisibility(View.VISIBLE);
                    eligibleCheckBox.setChecked(true);
                }
            }
        });

        makeUpCheckBox = (CheckBox)v.findViewById(R.id.lessonViewMakeUpCheckBox);
        eligibleCheckBox = (CheckBox)v.findViewById(R.id.lessonViewEligibleCheckBox);

        if(!isNewLesson) {
            loadViewData();
        }
        else {
            showLessonEdit();
        }

        return v;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }












    public void editLesson() {
        isEditing = true;
        loadEditData();
        showLessonEdit();
    }

    public void saveLesson() {
        saveLessonData();
        loadViewData();
        showLessonView();
        isEditing = false;
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
