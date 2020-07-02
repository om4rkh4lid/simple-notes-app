package com.example.mynote.Utilities;

import android.app.Application;

import com.example.mynote.Data.Note;
import com.example.mynote.Data.NoteRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class HomeViewModel extends AndroidViewModel {

    //reference to the repository
    NoteRepository mNoteRepo;

    //cached version of the data
    LiveData<List<Note>> mNotes;


    public HomeViewModel(Application application) {
        super(application);
        mNoteRepo = NoteRepository.getInstance(application);
        mNotes = mNoteRepo.getAllNotes();
    }


    public LiveData<List<Note>> getAllNotes(){
        return mNotes;
    }

    public void insert(Note note){
        mNoteRepo.insert(note);
    }

    public void delete(Note note){
        mNoteRepo.delete(note);
    }

}
