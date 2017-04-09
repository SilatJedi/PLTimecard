package com.silatsaktistudios.plmgr.ListViewArrayAdapters.MainActivity;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.silatsaktistudios.plmgr.Models.Lesson;
import com.silatsaktistudios.plmgr.R;

import java.util.Date;
import java.util.List;

/**
 * Created by james on 1/1/17.
 */

public class LessonListViewArrayAdapter extends ArrayAdapter<String> {
    private Context context;
//    private String[] names;
//    private Date[] dates;
//    private boolean[] showedUps, eligibles, makeUps;
    private List<Lesson> lessons;


    public LessonListViewArrayAdapter(Context context, List<Lesson> lessons) {
        super(context, R.layout.timecard_list_item, new String[lessons.size()]);//workaround, using the string array to define number of list items nothing shows up with out it
        this.context = context;
//        this.names = names;
//        this.dates = dates;
//        this.showedUps = showedUps;
//        this.eligibles = eligibles;
//        this.makeUps = makeUps;
        this.lessons = lessons;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.timecard_list_item, parent, false);

        if (position % 2 == 1) {
            rowView.setBackgroundColor(Color.LTGRAY);
        }

        Lesson lesson = lessons.get(position);

        TextView studentName = (TextView) rowView.findViewById(R.id.timecardItemStudentName);
//        studentName.setText(names[position]);
        studentName.setText(lesson.getStudentName());

        TextView dateTime = (TextView) rowView.findViewById(R.id.timecardItemDateTime);
        TextView showedUp = (TextView)rowView.findViewById(R.id.timecardShowedStatus);
        TextView eligible = (TextView)rowView.findViewById(R.id.timecardEligibleStatus);
        TextView makeUp = (TextView)rowView.findViewById(R.id.makeUpStatus);

//        if (!names[position].equals("No Lessons Found")) {
//            if (showedUps[position]) {
            if (lesson.didShowUp()) {
                showedUp.setText("Showed Up");
                eligible.setText("");
            } else {
                showedUp.setText("No Show");
                eligible.setVisibility(View.VISIBLE);

//                if (eligibles[position]) {
                if(lesson.isEligible()) {
                    eligible.setText("Eligible");
                } else {
                    eligible.setText("Not Eligible");
                }
            }

//            if (makeUps[position]) {
            if(lesson.isMakeUp()) {
                makeUp.setVisibility(View.VISIBLE);
                makeUp.setText("Make Up Lesson");
            }

            dateTime.setText(android.text.format.DateFormat.format("EEE d MMM, yyyy h:mm a", /*dates[position]*/lesson.getDate()));
//        }
//        else {
//            showedUp.setText("");
//            eligible.setText("");
//            makeUp.setText("");
//            dateTime.setText("");
//        }
        return rowView;
    }
}
