package com.example.mysemester.Features.Show;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysemester.Database.DatabaseQueryClass;
import com.example.mysemester.Features.Create.Subject;
import com.example.mysemester.Features.Show.StudentListActivity;
import com.example.mysemester.Features.Update.SubjectUpdateDialogFragment;
import com.example.mysemester.Features.Update.SubjectUpdateListener;
import com.example.mysemester.R;
import com.example.mysemester.Util.Config;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;

public class SubjectListRecyclerViewAdapter extends RecyclerView.Adapter<CustomViewHolder_Subject> {
    private Context context;
    private List<Subject> subjectList;
    private DatabaseQueryClass databaseQueryClass;
    public SubjectListRecyclerViewAdapter(Context context, List<Subject> subjectList) {
        this.context = context;
        this.subjectList = subjectList;
        databaseQueryClass = new DatabaseQueryClass(context);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @Override
    public CustomViewHolder_Subject onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.subject_item, parent, false);
        return new CustomViewHolder_Subject(view);
    }
    @Override
    public void onBindViewHolder(CustomViewHolder_Subject holder, int position) {
        final int itemPosition = position;
        final Subject subject = subjectList.get(position);

        holder.subjectNameTextView.setText(subject.getSubject_name());
        holder.subjectCodeTextView.setText(Long.toString(subject.getSubject_code()));
        holder.subjectCreditTextView.setText(Long.toString(subject.getSubject_credit()));

        holder.crossButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure, You wanted to delete this subject?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                deleteSubject(itemPosition);
                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        holder.editButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubjectUpdateDialogFragment subjectUpdateDialogFragment = SubjectUpdateDialogFragment.newInstance(subject.getSubject_code(), itemPosition, new SubjectUpdateListener() {
                    @Override
                    public void onSubjectInfoUpdated(Subject subject, int position) {
                        subjectList.set(position, subject);
                        notifyDataSetChanged();
                    }
                });
                subjectUpdateDialogFragment.show(((SubjectListActivity) context).getSupportFragmentManager(), Config.UPDATE_SUBJECT);
            }
        });
    }
    private void deleteSubject(int position) {
        Subject subject = subjectList.get(position);
        long count = databaseQueryClass.deleteSubjectByCode(subject.getSubject_code());

        if(count>0){
            subjectList.remove(position);
            notifyDataSetChanged();
            ((SubjectListActivity) context).viewVisibility();
            ((SubjectListActivity) context).Counter();
            Toast.makeText(context, "Subject deleted successfully", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(context, "Subject not deleted. Something wrong!", Toast.LENGTH_LONG).show();

    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }
}
