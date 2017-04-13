package com.silatsaktistudios.plmgr.ListViewArrayAdapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.silatsaktistudios.plmgr.Models.Lesson;
import com.silatsaktistudios.plmgr.R;

import java.util.List;

/**
 * Created by james on 1/1/17.
 */

public class LessonListViewArrayAdapter extends ArrayAdapter<String> {
    private Context context;
    private List<Lesson> lessons;


    public LessonListViewArrayAdapter(Context context, List<Lesson> lessons) {
        super(context, R.layout.lesson_list_item, new String[lessons.size()]);//workaround, using the string array to define number of list items nothing shows up with out it
        this.context = context;
        this.lessons = lessons;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.lesson_list_item, parent, false);

//        if (position % 2 == 1) {
//            if(Build.VERSION.SDK_INT < 23){
//                rowView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryLight));
//            }
//            else {
//                rowView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryLight, null));
//            }
//        }

        Lesson lesson = lessons.get(position);

        TextView studentName = (TextView) rowView.findViewById(R.id.timecardItemStudentName);
        studentName.setText(lesson.getStudentName());

        TextView dateTime = (TextView) rowView.findViewById(R.id.timecardItemDateTime);
        TextView showedUp = (TextView)rowView.findViewById(R.id.timecardShowedStatus);
        TextView eligible = (TextView)rowView.findViewById(R.id.timecardEligibleStatus);
        TextView makeUp = (TextView)rowView.findViewById(R.id.makeUpStatus);

            if (lesson.didShowUp()) {
                showedUp.setText("Showed Up");
                eligible.setText("");
            } else {
                showedUp.setText("No Show");
                eligible.setVisibility(View.VISIBLE);

                if(lesson.isEligible()) {
                    eligible.setText("Eligible");
                } else {
                    eligible.setText("Not Eligible");
                }
            }

            if(lesson.isMakeUp()) {
                makeUp.setVisibility(View.VISIBLE);
                makeUp.setText("Make Up Lesson");
            }

            dateTime.setText(android.text.format.DateFormat
                    .format("EEE MMM d, yyyy h:mm a", lesson.getDate()));

        return rowView;
    }
}
