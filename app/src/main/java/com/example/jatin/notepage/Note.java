package com.example.jatin.notepage;

public class Note {

    private String title, data;

    Note(){}

    Note(String title, String data){
        this.title = title;
        this.data = data;
    }

    //getter methods
    public String getTitle(){
        return this.title;
    }

    public String getData(){
        return this.data;
    }

    //setter methods
    public void setTitle(String title){
        this.title = title;
    }

    public void setData(String data){
        this.data = data;
    }

}