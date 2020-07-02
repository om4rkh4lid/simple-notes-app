package com.example.mynote.Data;

import android.app.Application;

import java.util.List;
import java.util.concurrent.ExecutorService;

import androidx.lifecycle.LiveData;

public class NoteRepository {
    /**
     * this class is an abstraction layer that allows the app logic to access data from multiple backends.
     * it may seem redundant since we only have one source of data but it's a best practice to use it.
     */

    //we need access to the Dao object as opposed to the whole database
    //since it can handle all read/write operations for us
    private NoteDao mNoteDao;

    //this is where we cache the words we receive
    private LiveData<List<Note>> mNotes;

    private LiveData<Note> queryNote;

    //reference to the database ExecutorThread to be used with db operations
    private ExecutorService dbWriteExecutor;

    //singleton instance
    private static NoteRepository INSTANCE;

    //lock
    private static final Object LOCK = new Object();


    //constructor for the class
    //we pass in an application since it is required to get the Room database instance
    //since this class is not an activity therefore it doesn't extend Context and we can't use this.getApplicationContext()
    //that means we will pass this.getApplicationContext() to the constructor from the activity that calls it
    private NoteRepository(Application application){
        mNoteDao = NoteRoomDatabase.getINSTANCE(application.getApplicationContext()).getNoteDao();
        dbWriteExecutor = NoteRoomDatabase.dbReadWriteExecutor;
        mNotes = mNoteDao.getAllNotes();
    }


    //for singleton
    public static NoteRepository getInstance(Application application){
        if(INSTANCE == null){
            synchronized (LOCK){
                if(INSTANCE == null){
                    INSTANCE = new NoteRepository(application);
                }
            }
        }
        return INSTANCE;
    }

    //return the notes
    public LiveData<List<Note>> getAllNotes(){
        return mNotes;
    }

    //insert note
    //perform on separate executor thread o.w. Room will throw an exception.
    public void insert(final Note note){
        dbWriteExecutor.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        mNoteDao.insert(note);
                    }
                }
        );

    }

    //insert note
    //perform on separate executor thread o.w. Room will throw an exception.
    public void delete(final Note note){
        dbWriteExecutor.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        mNoteDao.delete(note);
                    }
                }
        );
    }

    public void update(final Note note){
        dbWriteExecutor.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        mNoteDao.updateNote(note);
                    }
                }
        );
    }



    public LiveData<Note> getNoteById(final int id)  {
        return mNoteDao.getNoteById(id);
    }



}
