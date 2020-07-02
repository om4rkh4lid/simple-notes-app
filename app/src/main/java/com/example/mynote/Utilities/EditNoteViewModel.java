package com.example.mynote.Utilities;


import com.example.mynote.Data.Note;
import com.example.mynote.Data.NoteRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class EditNoteViewModel extends ViewModel {

    NoteRepository mNoteRepo;
    LiveData<Note> mCurrentNote;

    public EditNoteViewModel(NoteRepository repository, int noteId) {
        mNoteRepo = repository;
        mCurrentNote = noteId == 0 ? null : mNoteRepo.getNoteById(noteId);
    }

    public LiveData<Note> getCurrentNote() {
        return mCurrentNote;
    }

    public void saveNewNote(Note note){
        mNoteRepo.insert(note);
    }

    public void updateNote(Note note){
        mNoteRepo.update(note);
    }

    public void deleteNote(Note note){
        mNoteRepo.delete(note);
    }

}
