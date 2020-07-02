package com.example.mynote.Utilities;

import com.example.mynote.Data.NoteRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class EditNoteViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    /**
     * The sole purpose of this class is just so we can use EditNoteViewModel's constructor
     * since in the usual way we get an instance using the default constructor as we just pass
     * the class to the ViewModelProvider.
     * and that doesn't work for us since we need to pass the database and id to the viewmodel
     */

    private final NoteRepository mRepository;
    private final int mNoteId;

    public EditNoteViewModelFactory(NoteRepository repository, int noteId){
        mRepository = repository;
        mNoteId = noteId;
    }

    //this is the method that creates the viewModel that will be returned to the activity


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new EditNoteViewModel(mRepository, mNoteId);
    }
}
