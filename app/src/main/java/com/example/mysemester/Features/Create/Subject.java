package com.example.mysemester.Features.Create;

public class Subject {


    private long id;
    private String subject_name;
    private long subject_code;
    private long subject_credit;

    public Subject(int id, String subject_name, long subject_code, long subject_credit) {
        this.id = id;
        this.subject_name = subject_name;
        this.subject_code = subject_code;
        this.subject_credit = subject_credit;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public long getSubject_code() {
        return subject_code;
    }

    public void setSubject_code(long subject_code) {
        this.subject_code = subject_code;
    }

    public long getSubject_credit() {
        return subject_credit;
    }

    public void setSubject_credit(long subject_credit) {
        this.subject_credit = subject_credit;
    }
}
