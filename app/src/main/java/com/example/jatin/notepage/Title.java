package com.example.jatin.notepage;

/**
 * Created by jatin on 08/02/17.
 */

public class Title {

    private String title;
    private int id;
    private String latestMessage;

    Title(){}

    Title(String title, int id, String latestMessage){
        this.title = title;
        this.id = id;
        this.latestMessage = latestMessage;
    }

    //getter methods
    public String getTitle(){
        return this.title;
    }
    public int getId(){
        return this.id;
    }
    public String getLatestMessage(){
        return this.latestMessage;
    }

    //setter methods
    public void setTitle(String title){
        this.title = title;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setLatestMessage(String latestMessage){
        this.latestMessage = latestMessage;
    }

}
