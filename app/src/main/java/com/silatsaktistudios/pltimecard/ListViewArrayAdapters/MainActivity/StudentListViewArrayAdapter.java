package com.silatsaktistudios.pltimecard.ListViewArrayAdapters.MainActivity;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.silatsaktistudios.pltimecard.R;

/**
 * Created by james on 12/18/16.
 */

public class StudentListViewArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] names, enrollmentTypes, ranks;


    public StudentListViewArrayAdapter(Context context, String[] names, String[] enrollmentTypes,
                                       String[] ranks) {
        super(context, R.layout.student_list_item, names);
        this.context = context;
        this.names = names;
        this.enrollmentTypes = enrollmentTypes;
        this.ranks = ranks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.student_list_item, parent, false);

        if (position % 2 == 1) {
            rowView.setBackgroundColor(Color.LTGRAY);
        }

        TextView studentName = (TextView) rowView.findViewById(R.id.studentListName);
        studentName.setText(names[position]);
        TextView enrollmentType = (TextView)rowView.findViewById(R.id.studentListEnrollmentType);
        enrollmentType.setText(enrollmentTypes[position]);
        TextView rank = (TextView)rowView.findViewById(R.id.studentListRank);
        rank.setText(ranks[position]);

        return rowView;
    }
}
