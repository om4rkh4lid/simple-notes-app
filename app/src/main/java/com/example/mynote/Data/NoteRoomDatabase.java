package com.example.mynote.Data;



import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabase.Callback;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteRoomDatabase extends RoomDatabase {

    /**Executors for running background tasks*/
    //an Executor is a reusable thread
    //an ExecutorService is an Executor that provides services for termination
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService dbReadWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    /*****************************************/


    //abstract "getter" method for each DAO inside the database class
    public abstract NoteDao getNoteDao();

    //lock for the synchronized block
    private static final Object LOCK = new Object();


    //volatile means the memory instance remains inside the
    // main memory rather than be copied into the cache of a processor
    //static instance for the singleton pattern
    private static volatile NoteRoomDatabase INSTANCE;

    //only one thread can access this method at a time.
    public static NoteRoomDatabase getINSTANCE(Context context){
        if(INSTANCE == null){
            synchronized (LOCK){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NoteRoomDatabase.class,
                            "note_database")
//                            .addCallback(populateOnOpen)
//                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    //this is a method that performs some action on our ExecutorService whenever our db is opened
    //part of RoomDatabase.Callback which is a class for Callback methods associated with RoomDatabase
    static Callback populateOnOpen = new Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            dbReadWriteExecutor.execute(
                    new Runnable() {
                        @Override
                        public void run() {
                            INSTANCE.getNoteDao().insert(
                                    new Note(
                                    0,
                                    "Default Note",
                                    "00/00/0000",
                                    "Empty"
                            ));
                        }
                    }
            );
        }
    };




}
