package com.example.mysemester.Features.Show;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysemester.Database.DatabaseQueryClass;
import com.example.mysemester.Features.Create.Student;
import com.example.mysemester.Features.Update.StudentUpdateDialogFragment;
import com.example.mysemester.Features.Update.StudentUpdateListener;
import com.example.mysemester.R;
import com.example.mysemester.Util.Config;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;

public class StudentListRecyclerViewAdapter extends RecyclerView.Adapter<CustomViewHolder_Student> {
    private Context context;
    private List<Student> studentList;
    private DatabaseQueryClass databaseQueryClass;
    TextView numStudentTextView, numSubjectTextView, numTakenSubjectTextView;

    public StudentListRecyclerViewAdapter(Context context, List<Student> studentList) {
        this.context = context;
        this.studentList = studentList;
        databaseQueryClass = new DatabaseQueryClass(context);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @Override
    public CustomViewHolder_Student onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_item, parent, false);
        return new CustomViewHolder_Student(view);
    }
    @Override
    public void onBindViewHolder(CustomViewHolder_Student holder, int position) {
        final int itemPosition = position;
        final Student student = studentList.get(position);

        holder.nameTextView.setText(student.getName());
        holder.registrationNumTextView.setText(String.valueOf(student.getRegistrationNumber()));
        holder.phoneTextView.setText(student.getPhoneNumber());
        holder.emailTextView.setText(student.getEmail());

        holder.crossButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure, You wanted to delete this student?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                deleteStudent(itemPosition);

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
                StudentUpdateDialogFragment studentUpdateDialogFragment = StudentUpdateDialogFragment.newInstance(student.getRegistrationNumber(), itemPosition, new StudentUpdateListener() {
                    @Override
                    public void onStudentInfoUpdated(Student student, int position) {
                        studentList.set(position, student);
                        notifyDataSetChanged();
                    }
                });
                studentUpdateDialogFragment.show(((StudentListActivity) context).getSupportFragmentManager(), Config.UPDATE_STUDENT);
            }
        });
    }
    private void deleteStudent(int position) {
        Student student = studentList.get(position);
        long count = databaseQueryClass.deleteStudentByRegNum(student.getRegistrationNumber());

        if(count>0){
            studentList.remove(position);
            notifyDataSetChanged();
            ((StudentListActivity) context).viewVisibility();
            ((StudentListActivity) context).Counter();
            Toast.makeText(context, "Student deleted successfully", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(context, "Student not deleted. Something wrong!", Toast.LENGTH_LONG).show();

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }
}
