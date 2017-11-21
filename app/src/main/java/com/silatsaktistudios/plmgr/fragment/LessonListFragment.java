package com.silatsaktistudios.plmgr.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.silatsaktistudios.plmgr.R;
import com.silatsaktistudios.plmgr.adapter.LessonListViewArrayAdapter;
import com.silatsaktistudios.plmgr.data.LessonData;
import com.silatsaktistudios.plmgr.model.Lesson;

import java.util.List;


public class LessonListFragment extends Fragment {

    public interface OnFragmentInteractionListener {
        void onLessonListItemClick(int lessonId);
        void onAddLessonButtonClick();
    }


    private OnFragmentInteractionListener mListener;
    private EditText searchEditText;
    private ListView lessonListView;

    public LessonListFragment() {
        // Required empty public constructor
    }

    public static LessonListFragment newInstance() {
        return new LessonListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lesson_list, container, false);

        searchEditText = v.findViewById(R.id.lessonSearchEditText);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setUpLessonList(LessonData.lessonList(charSequence.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        lessonListView = v.findViewById(R.id.lessonListView);
        lessonListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int lessonID = LessonData.lessonList(searchEditText.getText().toString())
                        .get(position)
                        .getId();
                mListener.onLessonListItemClick(lessonID);
            }
        });
        lessonListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long l) {
                new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Light_Dialog_Alert)
                        .setTitle("Delete Lesson?")
                        .setMessage("Are you sure that you want to do this? Please note that this cannot be undone.")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String filter = searchEditText.getText().toString();
                                LessonData.delete(LessonData.lessonList(filter).get(position).getId());
                                setUpLessonList(LessonData.lessonList(filter));
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

        FloatingActionButton addLessonButton = v.findViewById(R.id.addLessonButton);
        addLessonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onAddLessonButtonClick();
            }
        });

        setUpLessonList(LessonData.lessonList(""));
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

    private void setUpLessonList(List<Lesson> lessons) {
        LessonListViewArrayAdapter lessonListViewArrayAdapter = new LessonListViewArrayAdapter(
                getActivity(),
                lessons);
        lessonListView.setAdapter(lessonListViewArrayAdapter);
    }

}
