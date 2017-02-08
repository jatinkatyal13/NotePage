package com.example.jatin.notepage;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jatin on 08/02/17.
 */

public class Database extends SQLiteOpenHelper{

    static final private String DATABASE_NAME = "notes.db";
    static final private int DATABASE_VERSION = 1;
    private static SQLiteDatabase database;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        init(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //TODO
    }

    public class DatabaseException extends Exception{

        String message = "Unknown Exception";

        DatabaseException(String message){
            this.message = message;
        }

        @Override
        public String toString(){
            return message;
        }
    }

    //initializing the database
    public void init(SQLiteDatabase database){
        String sql;

        //creating Titles table
        /*
            id -> integer primary key
            title -> string
        */
        sql = "CREATE TABLE IF NOT EXISTS Titles (id integer primary key autoincrement, title varchar(25));";
        database.execSQL(sql);

        //creating Notes table
        /*
            id -> integer primary key
            title_id -> integer foreign key
            message -> string
        */
        sql = "CREATE TABLE IF NOT EXISTS Notes (id integer primary key autoincrement, title_id int, message text);";
        database.execSQL(sql);

    }

    //method to add new title
    public void addTitle(String name) {
        SQLiteDatabase database = this.getWritableDatabase();

        //encoding incoming string for potential sql injections
        try {
            name = new String (name.getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //building query
        String sql = "INSERT INTO Titles(title) VALUES ('" + name.trim() + "');";
        //executing query
        database.execSQL(sql);

    }

    //method to add new note
    public void addNote(String name, int  title_id) throws DatabaseException{
        SQLiteDatabase database = this.getWritableDatabase();

        String sql;

        //encoding incoming string for potential sql injections
        try {
            name = new String (name.getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //checking if title_id is correct or not
        sql = "SELECT COUNT(*) FROM Titles WHERE id = " + title_id;
        Cursor resultSet = database.rawQuery(sql, null);
        resultSet.moveToFirst();
        if (resultSet.getInt(0) == 0) throw new DatabaseException("title_id can't be found !");
        else {
            sql = "INSERT INTO Notes(title_id, message) VALUES (" + title_id + ", '" + name + "');";
            database.execSQL(sql);
        }
    }

    //method to get all the titles with truncated latest message
    public List<Title> getTitles(){
        SQLiteDatabase database = this.getReadableDatabase();
        List<Title> list = new ArrayList<>();

        String sql = "SELECT Titles.id, title, max(Notes.id), message FROM Titles, Notes WHERE Titles.id = Notes.title_id GROUP BY title_id;";
        Cursor resultSet = database.rawQuery(sql, null);

        int size = resultSet.getCount();

        for (int i=0; i<size; i++){
            list.add(new Title(resultSet.getString(1), resultSet.getInt(0), resultSet.getString(3)));
            resultSet.moveToNext();
        }

        return list;
    }

    //method to get all the notes based on title id
    public List<Note> getNotes(int title_id){
        SQLiteDatabase database = this.getReadableDatabase();
        List<Note> list = new ArrayList<>();

        String sql = "SELECT id, message FROM Notes WHERE title_id = " + title_id;
        Cursor resultSet = database.rawQuery(sql, null);

        int size = resultSet.getCount();

        for (int i=0; i<size; i++){
            list.add(new Note(resultSet.getString(0), resultSet.getInt(1)));
            resultSet.moveToNext();
        }

        return list;
    }

}
