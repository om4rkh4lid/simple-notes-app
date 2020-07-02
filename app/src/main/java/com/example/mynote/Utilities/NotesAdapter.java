package com.example.mynote.Utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.mynote.Data.Note;
import com.example.mynote.R;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    final LayoutInflater mInflater;
    List<Note> mNoteList;
    NoteClickListener mListener;

    public class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTitleTextView, mLastEditedTextView;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.tv_title);
            mLastEditedTextView = itemView.findViewById(R.id.tv_last_edited);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int id = mNoteList.get(getAdapterPosition()).getId();
            mListener.onNoteClicked(id);
        }
    }

    public NotesAdapter(Context context, NoteClickListener listener){
        mInflater = LayoutInflater.from(context);
        mListener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.note_item_layout, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note current = mNoteList.get(position);
        holder.mTitleTextView.setText(current.getTitle());
        holder.mLastEditedTextView.setText(DateTime.getDateTimeForDisplay(current.getTimeStamp()));
    }

    @Override
    public int getItemCount() {
        return mNoteList == null ? 0 : mNoteList.size();
    }


    public void setData(List<Note> data){
        mNoteList = data;
        notifyDataSetChanged();
    }

    public List<Note> getNoteList() {
        return mNoteList;
    }


    public Note getNoteAtPosition(int position){
        return mNoteList.get(position);
    }


}
