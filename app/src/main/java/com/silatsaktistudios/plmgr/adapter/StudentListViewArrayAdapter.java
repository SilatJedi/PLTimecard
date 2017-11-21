package com.silatsaktistudios.plmgr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.silatsaktistudios.plmgr.model.Student;
import com.silatsaktistudios.plmgr.R;

import java.util.List;

/**
 * Created by james on 12/18/16.
 */

public class StudentListViewArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
//    private final String[] names, enrollmentTypes, ranks;
    private List<Student> students;

    public StudentListViewArrayAdapter(Context context, List<Student> students) {
        super(context, R.layout.student_list_item, new String[students.size()]);//workaround, using the string array to define number of list items nothing shows up with out it
        this.context = context;
//        this.names = names;
//        this.enrollmentTypes = enrollmentTypes;
//        this.ranks = ranks;
        this.students = students;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.student_list_item, parent, false);

//        if (position % 2 == 1) {
//            rowView.setBackgroundColor(Color.LTGRAY);
//        }

        Student student = students.get(position);

        TextView studentName = (TextView) rowView.findViewById(R.id.studentListName);
//        studentName.setText(names[position]);
        studentName.setText(student.getFullName());

        TextView enrollmentType = (TextView)rowView.findViewById(R.id.studentListEnrollmentType);
//        enrollmentType.setText(enrollmentTypes[position]);
        enrollmentType.setText(student.getEnrollmentType());

        TextView rank = (TextView)rowView.findViewById(R.id.studentListRank);
//        rank.setText(ranks[position]);
        rank.setText(student.getRank());

        return rowView;
    }
}
