package com.example.jatin.notepage;

public class Note {

    private int id;
    private String message;

    Note(){}

    Note(String message){
        this.message = message;
    }

    Note(String message, int id){
        this.id = id;
        this.message = message;
    }

    //getter methods
    public String getMessage(){
        return this.message;
    }
    public int getId(){
        return this.id;
    }

    //setter methods
    public void setMessage(String message){
        this.message = message;
    }
    public void setId(int id){
        this.id = id;
    }

}