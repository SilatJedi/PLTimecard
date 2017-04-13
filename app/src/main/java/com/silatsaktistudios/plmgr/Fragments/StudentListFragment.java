package com.silatsaktistudios.plmgr.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.silatsaktistudios.plmgr.DataLogic.StudentData;
import com.silatsaktistudios.plmgr.ListViewArrayAdapters.StudentListViewArrayAdapter;
import com.silatsaktistudios.plmgr.Models.Student;
import com.silatsaktistudios.plmgr.R;

import java.util.List;


public class StudentListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private EditText searchEditText;
    private ListView studentListView;
    private boolean isFiltered;

    public StudentListFragment() {
        // Required empty public constructor
    }


    public static StudentListFragment newInstance() {
        StudentListFragment fragment = new StudentListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_student_list, container, false);

        searchEditText = (EditText)v.findViewById(R.id.studentSearchEditText);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() > 0) {
                    isFiltered = true;
                    setUpStudentList(StudentData.filteredStudentList(charSequence.toString()));
                }
                else {
                    isFiltered = false;
                    setUpStudentList(StudentData.studentList());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        studentListView = (ListView)v.findViewById(R.id.studentListView);
        studentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int studentId;

                if(isFiltered) {
                    studentId = StudentData.filteredStudentList(searchEditText.getText().toString())
                            .get(position)
                            .getId();
                }
                else {
                    studentId = StudentData.studentList().get(position).getId();
                }

                mListener.onStudentListItemClick(studentId);
            }
        });
        studentListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Light_Dialog_Alert)
                        .setTitle("Delete Student?")
                        .setMessage("Are you sure that you want to do this? Please note that this cannot be undone.")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (isFiltered) {
                                    String filter = searchEditText.getText().toString();
                                    StudentData.delete(StudentData.filteredStudentList(filter).get(position).getId());
                                    setUpStudentList(StudentData.filteredStudentList(filter));
                                }
                                else {
                                    StudentData.delete(StudentData.studentList().get(position).getId());
                                    setUpStudentList(StudentData.studentList());
                                }

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
                return false;
            }
        });

        FloatingActionButton addStudentButton = (FloatingActionButton)v.findViewById(R.id.addStudentButton);
        addStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onAddStudentButtonClick();
            }
        });

        setUpStudentList(StudentData.studentList());

        return v;
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
        void onStudentListItemClick(int studentId);
        void onAddStudentButtonClick();
    }

    private void setUpStudentList(List<Student> students) {
        StudentListViewArrayAdapter studentListViewArrayAdapter = new StudentListViewArrayAdapter(
                getActivity(),
                students);
        studentListView.setAdapter(studentListViewArrayAdapter);
    }
}
