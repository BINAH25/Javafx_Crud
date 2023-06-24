package com.example.crud;

public class StudentData {
    private Integer student_number;
    private String full_name;
    private String year;
    private  String course;
    private String gender;

    public StudentData(Integer student_number,String full_name,String year,String course, String gender){
        this.student_number = student_number;
        this.full_name = full_name;
        this.year = year;
        this.course = course;
        this.gender = gender;
    }

    public String getCourse() {
        return course;
    }

    public Integer getStudent_number() {
        return student_number;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getGender() {
        return gender;
    }

    public String getYear() {
        return year;
    }
}
