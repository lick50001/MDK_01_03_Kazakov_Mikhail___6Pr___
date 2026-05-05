package com.example.notes_kazakov.presentations;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.notes_kazakov.R;
import com.example.notes_kazakov.datas.DbContext;
import com.example.notes_kazakov.datas.NotesContext;
import com.example.notes_kazakov.datas.RepoNotes;
import com.example.notes_kazakov.domains.models.Note;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class NotesActivity extends AppCompatActivity {
    GridLayout itemsParent;
    View bthAddNotes;
    EditText etSearch;
    DbContext dbContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        bthAddNotes = findViewById(R.id.bth_add_notes);
        itemsParent = findViewById(R.id.gl_notes);
        etSearch = findViewById(R.id.et_search);

        dbContext = new DbContext(this);

        NotesContext.LoadNotesFromDBToRepo();

        Toast.makeText(this, "Загружено заметок: " + RepoNotes.Notes.size(), Toast.LENGTH_SHORT).show();

        bthAddNotes.setOnClickListener(v -> {
            Intent intent = new Intent(this, NoteActivity.class);
            startActivity(intent);
        });

        etSearch.setOnKeyListener(SearchListener);

        LoadNotes(RepoNotes.Notes);
    }

    @Override
    protected void onResume(){
        super.onResume();
        NotesContext.LoadNotesFromDBToRepo();
        LoadNotes(RepoNotes.Notes);
        Toast.makeText(this, "Заметок в БД: " + RepoNotes.Notes.size(), Toast.LENGTH_SHORT).show();
    }

    public  void LoadNotes(ArrayList<Note> notes){
        itemsParent.removeAllViews();

        for (int i = 0; i < notes.size(); i ++){
            View item_notes = LayoutInflater.from(this).inflate(R.layout.activity_item_note, itemsParent, false);

            TextView tvTitle = item_notes.findViewById(R.id.tv_title);
            TextView tvText = item_notes.findViewById(R.id.tv_text);
            TextView tvDate = item_notes.findViewById(R.id.tv_date);

            tvTitle.setText(notes.get(i).title);
            tvDate.setText(notes.get(i).date);
            tvText.setText(notes.get(i).text);

            int Position = i;

            item_notes.setOnClickListener(v -> {
                Intent intentActivityNote = new Intent(this, NoteActivity.class);
                intentActivityNote.putExtra("position", Position);
                startActivity(intentActivityNote);
            });

            itemsParent.addView(item_notes);
        }
    }

    View.OnKeyListener SearchListener = (v, keyCode, event) -> {
        if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER) {
            String Search = etSearch.getText().toString().toLowerCase();

            ArrayList<Note> FindNotes = NotesContext.AllNotes().stream()
                    .filter(item -> item.text != null && item.text.toLowerCase().contains(Search) ||
                            item.title != null && item.title.toLowerCase().contains(Search))
                    .collect(Collectors.toCollection(ArrayList::new));

            LoadNotes(FindNotes);
        }
        return false;
    };
}