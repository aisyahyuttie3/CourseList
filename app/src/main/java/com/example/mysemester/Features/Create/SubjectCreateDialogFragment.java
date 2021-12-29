package com.example.mysemester.Features.Create;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.mysemester.Database.DatabaseQueryClass;
import com.example.mysemester.Util.Config;
import com.example.mysemester.R;
import androidx.fragment.app.DialogFragment;

public class SubjectCreateDialogFragment extends DialogFragment {
    private static SubjectCreateListener subjectCreateListener;

    private EditText subjectNameEditText;
    private EditText subjectCodeEditText;
    private EditText subjectCreditEditText;
    private Button createButton;
    private Button cancelButton;

    private String subjectNameString = "";
    private long subjectCodeNumber = -1;
    private long subjectCreditNumber = 0;

    public SubjectCreateDialogFragment() {
        // Required empty public constructor
    }
    public static SubjectCreateDialogFragment newInstance(String title, SubjectCreateListener listener){
        subjectCreateListener = listener;
        SubjectCreateDialogFragment subjectCreateDialogFragment = new SubjectCreateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        subjectCreateDialogFragment.setArguments(args);

        subjectCreateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return subjectCreateDialogFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_subject_create_dialog, container, false);

        subjectNameEditText = view.findViewById(R.id.subjectNameEditText);
        subjectCodeEditText = view.findViewById(R.id.subjectCodeEditText);
        subjectCreditEditText = view.findViewById(R.id.subjectCreditEditText);
        createButton = view.findViewById(R.id.createButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subjectNameString = subjectNameEditText.getText().toString();
                subjectCodeNumber = Integer.parseInt(subjectCodeEditText.getText().toString());
                subjectCreditNumber = Integer.parseInt(subjectCreditEditText.getText().toString());

                Subject subject = new Subject(-1, subjectNameString, subjectCodeNumber, subjectCreditNumber);

                DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(getContext());

                long id = databaseQueryClass.insertSubject(subject);

                if(id>0){
                    subject.setId(id);
                    subjectCreateListener.onSubjectCreated(subject);
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
