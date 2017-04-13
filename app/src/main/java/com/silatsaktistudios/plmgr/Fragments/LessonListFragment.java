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

import com.silatsaktistudios.plmgr.DataLogic.LessonData;
import com.silatsaktistudios.plmgr.ListViewArrayAdapters.LessonListViewArrayAdapter;
import com.silatsaktistudios.plmgr.Models.Lesson;
import com.silatsaktistudios.plmgr.R;

import java.util.List;


public class LessonListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private EditText searchEditText;
    private ListView lessonListView;
    private boolean isFiltered;

    public LessonListFragment() {
        // Required empty public constructor
    }

    public static LessonListFragment newInstance() {
        LessonListFragment fragment = new LessonListFragment();
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
        View v = inflater.inflate(R.layout.fragment_lesson_list, container, false);

        searchEditText = (EditText)v.findViewById(R.id.studentSearchEditText);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() > 0) {
                    isFiltered = true;
                    setUpLessonList(LessonData.filteredLessonList(charSequence.toString()));
                }
                else {
                    isFiltered = false;
                    setUpLessonList(LessonData.lessonList());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        lessonListView = (ListView)v.findViewById(R.id.lessonListView);
        lessonListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int lessonID;

                if(isFiltered) {
                    lessonID = LessonData.filteredLessonList(searchEditText.getText().toString())
                            .get(position)
                            .getId();
                }
                else {
                    lessonID = LessonData.lessonList().get(position).getId();
                }

                mListener.onLessonListItemClick(lessonID);
            }
        });
        lessonListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long l) {
                new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Light_Dialog_Alert)
                        .setTitle("Delete Lesson?")
                        .setMessage("Are you sure that you want to do this? Please note that this cannot be undone.")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (isFiltered) {
                                    String filter = searchEditText.getText().toString();
                                    LessonData.delete(
                                            LessonData.filteredLessonList(filter)
                                                    .get(position)
                                                    .getId());
                                    setUpLessonList(LessonData.filteredLessonList(filter));
                                }
                                else {
                                    LessonData.delete(LessonData.lessonList().get(position).getId());
                                    setUpLessonList(LessonData.lessonList());
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

        FloatingActionButton addLessonButton = (FloatingActionButton) v.findViewById(R.id.addLessonButton);
        addLessonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onAddLessonButtonClick();
            }
        });


        setUpLessonList(LessonData.lessonList());

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
        void onLessonListItemClick(int lessonId);
        void onAddLessonButtonClick();
    }



    private void setUpLessonList(List<Lesson> lessons) {
        LessonListViewArrayAdapter lessonListViewArrayAdapter = new LessonListViewArrayAdapter(
                getActivity(),
                lessons);
        lessonListView.setAdapter(lessonListViewArrayAdapter);
    }

}
