package com.silatsaktistudios.plmgr.ListViewArrayAdapters.MainActivity;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.silatsaktistudios.plmgr.R;

import java.util.Date;

/**
 * Created by james on 1/1/17.
 */

public class TimeCardListViewArrayAdapter extends ArrayAdapter<String> {
    private Context context;
    private String[] names;
    private Date[] dates;
    private boolean[] showedUps, eligibles, makeUps;


    public TimeCardListViewArrayAdapter(Context context, String[] names, Date[] dates,
                                        boolean[] showedUps, boolean[] eligibles, boolean[] makeUps) {
        super(context, R.layout.timecard_list_item, names);
        this.context = context;
        this.names = names;
        this.dates = dates;
        this.showedUps = showedUps;
        this.eligibles = eligibles;
        this.makeUps = makeUps;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.timecard_list_item, parent, false);

        if (position % 2 == 1) {
            rowView.setBackgroundColor(Color.LTGRAY);
        }

        TextView studentName = (TextView) rowView.findViewById(R.id.timecardItemStudentName);
        studentName.setText(names[position]);

        TextView dateTime = (TextView) rowView.findViewById(R.id.timecardItemDateTime);
        TextView showedUp = (TextView)rowView.findViewById(R.id.timecardShowedStatus);
        TextView eligible = (TextView)rowView.findViewById(R.id.timecardEligibleStatus);
        TextView makeUp = (TextView)rowView.findViewById(R.id.makeUpStatus);

        if (!names[position].equals("No Lessons Found")) {
            if (showedUps[position]) {
                showedUp.setText("Showed Up");
                eligible.setText("");
            } else {
                showedUp.setText("No Show");
                eligible.setVisibility(View.VISIBLE);

                if (eligibles[position]) {
                    eligible.setText("Eligible");
                } else {
                    eligible.setText("Not Eligible");
                }
            }

            if (makeUps[position]) {
                makeUp.setVisibility(View.VISIBLE);
                makeUp.setText("Make Up Lesson");
            }

            dateTime.setText(android.text.format.DateFormat.format("EEE d MMM, yyyy h:mm a", dates[position]));
        }
        else {
            showedUp.setText("");
            eligible.setText("");
            makeUp.setText("");
            dateTime.setText("");
        }
        return rowView;
    }
}
