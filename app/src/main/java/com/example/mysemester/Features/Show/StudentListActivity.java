package com.example.mysemester.Features.Show;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysemester.Database.DatabaseQueryClass;
import com.example.mysemester.Features.Create.Student;
import com.example.mysemester.Features.Create.StudentCreateDialogFragment;
import com.example.mysemester.Features.Create.StudentCreateListener;
import com.example.mysemester.R;
import com.example.mysemester.Util.Config;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;


public class StudentListActivity extends AppCompatActivity implements StudentCreateListener {
    private DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);

    private List<Student> studentList = new ArrayList<>();
    private TextView studentListEmptyTextView;
    private RecyclerView recyclerView;
    private StudentListRecyclerViewAdapter studentListRecyclerViewAdapter;
    TextView numStudentTextView, numSubjectTextView, numTakenSubjectTextView;
    private DatabaseQueryClass db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Logger.addLogAdapter(new AndroidLogAdapter());

        recyclerView = (RecyclerView) findViewById(R.id.studentRecyclerView);
        studentListEmptyTextView = (TextView) findViewById(R.id.emptyStudentListTextView);

        studentList.addAll(databaseQueryClass.getAllStudent());

        studentListRecyclerViewAdapter = new StudentListRecyclerViewAdapter(this, studentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(studentListRecyclerViewAdapter);

        viewVisibility();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStudentCreateDialog();
            }
        });
        Counter();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_student, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_subjectList){
            Intent intent = new Intent(StudentListActivity.this, SubjectListActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    private void openStudentCreateDialog() {
        StudentCreateDialogFragment studentCreateDialogFragment = StudentCreateDialogFragment.newInstance("Create Student", this);
        studentCreateDialogFragment.show(getSupportFragmentManager(), Config.CREATE_STUDENT);
    }

    @Override
    public void onStudentCreated(Student student) {
        studentList.add(student);
        studentListRecyclerViewAdapter.notifyDataSetChanged();
        viewVisibility();
        Logger.d(student.getName());
        Counter();
    }
    public void viewVisibility() {
        if(studentList.isEmpty())
            studentListEmptyTextView.setVisibility(View.VISIBLE);
        else
            studentListEmptyTextView.setVisibility(View.GONE);
    }

    public void Counter(){
        numStudentTextView = (TextView) findViewById(R.id.numStudentTextView);
        numSubjectTextView = (TextView) findViewById(R.id.numSubjectTextView);
        db = new DatabaseQueryClass(this);
        numStudentTextView.setText(Integer.toString(db.studentCount()));
        numSubjectTextView.setText(Integer.toString(db.subjectCount()));
    }

}