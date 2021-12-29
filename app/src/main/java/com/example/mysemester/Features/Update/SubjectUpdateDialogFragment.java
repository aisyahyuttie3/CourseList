package com.example.mysemester.Features.Update;

import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.mysemester.Database.DatabaseQueryClass;
import com.example.mysemester.Features.Create.Subject;
import com.example.mysemester.Features.Update.SubjectUpdateListener;
import com.example.mysemester.R;
import com.example.mysemester.Util.Config;

public class SubjectUpdateDialogFragment extends DialogFragment{
    private static long subjectCode;
    private static int subjectItemPosition;
    private static SubjectUpdateListener subjectUpdateListener;

    private Subject mSubject;

    private EditText subjectNameEditText;
    private EditText subjectCodeEditText;
    private EditText subjectCreditEditText;
    private Button updateButton;
    private Button cancelButton;

    private String subjectNameString = "";
    private long subjectCodeNumber = -1;
    private long subjectCreditNumber = 0;

    private DatabaseQueryClass databaseQueryClass;
    public SubjectUpdateDialogFragment() {
        // Required empty public constructor
    }
    public static SubjectUpdateDialogFragment newInstance(long subjectCodeNumber, int position, SubjectUpdateListener listener){
        subjectCode = subjectCodeNumber;
        subjectItemPosition = position;
        subjectUpdateListener = listener;
        SubjectUpdateDialogFragment subjectUpdateDialogFragment = new SubjectUpdateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", "Update subject information");
        subjectUpdateDialogFragment.setArguments(args);

        subjectUpdateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return subjectUpdateDialogFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_subject_update_dialog, container, false);

        databaseQueryClass = new DatabaseQueryClass(getContext());

        subjectNameEditText = view.findViewById(R.id.subjectNameEditText);
        subjectCodeEditText = view.findViewById(R.id.subjectCodeEditText);
        subjectCreditEditText = view.findViewById(R.id.subjectCreditEditText);
        updateButton = view.findViewById(R.id.updateSubjectInfoButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        mSubject = databaseQueryClass.getSubjectByCode(subjectCode);

        if(mSubject!=null){
            subjectNameEditText.setText(mSubject.getSubject_name());
            subjectCodeEditText.setText(String.valueOf(mSubject.getSubject_code()));
            subjectCreditEditText.setText(String.valueOf(mSubject.getSubject_credit()));

            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    subjectNameString = subjectNameEditText.getText().toString();
                    subjectCodeNumber = Integer.parseInt(subjectCodeEditText.getText().toString());
                    subjectCreditNumber = Integer.parseInt(subjectCreditEditText.getText().toString());

                    mSubject.setSubject_name(subjectNameString);
                    mSubject.setSubject_code(subjectCodeNumber);
                    mSubject.setSubject_credit(subjectCreditNumber);

                    long id = databaseQueryClass.updateSubjectInfo(mSubject);

                    if(id>0){
                        subjectUpdateListener.onSubjectInfoUpdated(mSubject, subjectItemPosition);
                        getDialog().dismiss();
                    }
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getDialog().dismiss();
                }
            });

        }

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            //noinspection ConstantConditions
            dialog.getWindow().setLayout(width, height);
        }
    }



}