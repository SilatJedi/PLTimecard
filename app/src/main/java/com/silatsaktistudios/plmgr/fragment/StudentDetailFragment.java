package com.silatsaktistudios.plmgr.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.silatsaktistudios.plmgr.R;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link StudentDetailFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link StudentDetailFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class StudentDetailFragment extends Fragment {

//    private OnFragmentInteractionListener mListener;

    LinearLayout selectStudentTypeLayout, parent1Layout, parent2Layout;
    TextView selectChildTextView, selectAdultTextView, studentFirstNameTextView, studentLastNameTextView, viewRankTextView,
            emailTextView, parent1FirstNameTextView, parent1LastNameTextView, viewParent1TypeTextView, editParent1TypeTextView, primaryPhoneTitle,
            viewPrimaryPhoneNumTextView, editPrimaryPhoneType, viewP2FirstName, viewP2LastName, viewP2Type,
            editP2Type, viewSecondaryPhoneNum, secondaryPhoneTitle, viewSecondaryPhoneType,
            editSecondaryPhoneType, editRankTextView;
    EditText editFirstName, editLastName, editEmail, editP1FirstName, editP1LastName,
            editPrimaryPhoneNum, editP2FirstName, editP2LastName, editSecondaryPhoneNum;

    public StudentDetailFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static StudentDetailFragment newInstance(int studentId) {
        StudentDetailFragment fragment = new StudentDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE},   //request specific permission from user
                    10);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_student_detail, container, false);

        selectStudentTypeLayout = (LinearLayout)v.findViewById(R.id.selectStudentTypeLayout);
        parent1Layout = (LinearLayout)v.findViewById(R.id.parent1Layout);
        parent2Layout = (LinearLayout)v.findViewById(R.id.parent2Layout);

        selectChildTextView = (TextView)v.findViewById(R.id.studentChildTextView);
        selectAdultTextView = (TextView)v.findViewById(R.id.studentAdultTextView);

        studentFirstNameTextView = (TextView)v.findViewById(R.id.studentViewFirstName);
        studentLastNameTextView = (TextView)v.findViewById(R.id.studentViewLastName);

        viewRankTextView = (TextView)v.findViewById(R.id.studentViewRank);
        editRankTextView = (TextView)v.findViewById(R.id.studentEditRank);

        emailTextView = (TextView)v.findViewById(R.id.studentViewEmail);

        parent1FirstNameTextView = (TextView)v.findViewById(R.id.studentViewP1FirstName);
        parent1LastNameTextView = (TextView)v.findViewById(R.id.studentViewP1LastName);
        viewParent1TypeTextView = (TextView)v.findViewById(R.id.studentViewP1Type);
        editParent1TypeTextView = (TextView)v.findViewById(R.id.studentEditP1Type);

        primaryPhoneTitle = (TextView)v.findViewById(R.id.primaryPhoneTitle);
        viewPrimaryPhoneNumTextView = (TextView)v.findViewById(R.id.studentViewPrimaryPhoneNum);
        editPrimaryPhoneType = (TextView)v.findViewById(R.id.studentEditPrimaryPhoneType);
        viewP2FirstName = (TextView)v.findViewById(R.id.studentParent2FirstName);
        viewP2LastName = (TextView)v.findViewById(R.id.studentViewP2LastName);
        viewP2Type = (TextView)v.findViewById(R.id.studentViewP2Type);
        editP2Type = (TextView)v.findViewById(R.id.studentEditP2Type);
        viewSecondaryPhoneNum = (TextView)v.findViewById(R.id.studentSecondaryPhoneNum);
        secondaryPhoneTitle = (TextView)v.findViewById(R.id.secondaryPhoneTitle);
        viewSecondaryPhoneType = (TextView)v.findViewById(R.id.studentViewSecondaryPhoneType);
        editSecondaryPhoneType = (TextView)v.findViewById(R.id.studentEditSecondaryPhoneType);


        editFirstName = (EditText)v.findViewById(R.id.studentEditFirstName);
        editLastName = (EditText)v.findViewById(R.id.studentEditLastName);
        editEmail = (EditText)v.findViewById(R.id.studentEditEmail);
        editP1FirstName = (EditText)v.findViewById(R.id.studentEditP1FirstName);
        editP1LastName = (EditText)v.findViewById(R.id.studentEditP1LastName);
        editPrimaryPhoneNum = (EditText)v.findViewById(R.id.studentEditPrimaryPhoneNum);
        editP2FirstName = (EditText)v.findViewById(R.id.studentEditP2FirstName);
        editP2LastName = (EditText)v.findViewById(R.id.studentEditP2LastName);
        editSecondaryPhoneNum = (EditText)v.findViewById(R.id.studentEditSecondaryPhoneNum);

        return v;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
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
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//    }
}
