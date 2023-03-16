package com.example.testdb;

import java.util.ArrayList;

public class StudentManageParent {

    //declare vars
    private String dateTitle;
    private ArrayList<StudentManageChild> childArrayList;

    //constructor method
    public StudentManageParent(String dateTitle, ArrayList<StudentManageChild> childArrayList){
        this.dateTitle = dateTitle;
        this.childArrayList = childArrayList;
    }

    //getters and setters
    public String getDateTitle(){
        return dateTitle;
    }

    public ArrayList<StudentManageChild> getChildArrayList(){
        return childArrayList;
    }

    public void setDateTitle(String dateTitle) {
        this.dateTitle = dateTitle;
    }

    public void setChildArrayList(ArrayList<StudentManageChild> childArrayList) {
        this.childArrayList = childArrayList;
    }
}
