package com.example.mynote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mynote.Data.Note;
import com.example.mynote.Data.NoteRepository;
import com.example.mynote.Utilities.HomeViewModel;
import com.example.mynote.Utilities.NoteClickListener;
import com.example.mynote.Utilities.NotesAdapter;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NoteClickListener {

    RecyclerView mNotesRecyclerView;
    NoteRepository mNoteRepo;
    NotesAdapter mNoteAdapter;
    Toast mToast;

    public static final String EXISTING_NOTE_ID_FLAG = "existing";
    public static final String NEW_NOTE_ID_FLAG = "new";
    private final int NEW_NOTE_ID = 0;

    //reference to the viewmodel
    HomeViewModel mHomeViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //get viewmodel for this activity
        mHomeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        //get instance to repository
        mNoteRepo = NoteRepository.getInstance(getApplication());

        // recyclerView initialisation
        mNotesRecyclerView = findViewById(R.id.rv_notes);
        mNoteAdapter = new NotesAdapter(this, this);

        //set recyclerview properties
        mNotesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mNotesRecyclerView.setAdapter(mNoteAdapter);
        mNotesRecyclerView.setHasFixedSize(true);


        //add an observer to the data returned by the NoteRepository's getAllNotes method
        //observe(lifecycle owner (to monitor it's lifecycle),
        //                          observer(that performs an action when data changes))
        mNoteRepo.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                //when the data changes we want to update the cached version in the Adapter class
                mNoteAdapter.setData(notes);
            }
        });

        /** ItemTouchHelper for the swipe functionality */
        ItemTouchHelper swipHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Note note = mNoteAdapter.getNoteAtPosition(position);
                mHomeViewModel.delete(note);
                if(mToast != null)
                    mToast.cancel();
                mToast = Toast.makeText(getApplicationContext(), R.string.note_deleted, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });

        swipHelper.attachToRecyclerView(mNotesRecyclerView);

    }

    public void startNewNoteActivity(View view) {

        //create intent object to start activity with
        Intent newNoteIntent = new Intent(this, EditNoteActivity.class);
        newNoteIntent.putExtra(NEW_NOTE_ID_FLAG, NEW_NOTE_ID);
        startActivity(newNoteIntent);

    }

    @Override
    public void onNoteClicked(int number) {

//        create intent object to start activity with
        Intent existingNoteIntent = new Intent(this, EditNoteActivity.class);
        existingNoteIntent.putExtra(EXISTING_NOTE_ID_FLAG, number);
        startActivity(existingNoteIntent);
    }







}
