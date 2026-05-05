package com.example.notes_kazakov.presentations;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.notes_kazakov.R;
import com.example.notes_kazakov.datas.NotesContext;
import com.example.notes_kazakov.datas.RepoNotes;
import com.example.notes_kazakov.domains.models.Note;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteActivity extends AppCompatActivity {

    Note note;

    EditText etTitle, etText;
    TextView tvDate;
    View bthSelectColor, bthBack, bthTrash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Date DateNow = new Date();
        SimpleDateFormat FormatForDateNow = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");

        bthSelectColor = findViewById(R.id.bth_select_color);
        bthBack = findViewById(R.id.bth_back);
        bthTrash = findViewById(R.id.bth_trash);
        etTitle = findViewById(R.id.et_title);
        etText = findViewById(R.id.et_text);
        tvDate = findViewById(R.id.tv_date);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            int Position = arguments.getInt("position");

            note = RepoNotes.Notes.get(Position);

            etTitle.setText(note.title);
            etText.setText(note.text);
        } else {
            bthTrash.setVisibility(View.GONE);
        }
        tvDate.setText("Отредактировано: " + FormatForDateNow.format(DateNow));

        bthSelectColor.setOnClickListener(v -> {
            Toast.makeText(this, "Выбор цвета недоступен", Toast.LENGTH_SHORT).show();
        });

        bthBack.setOnClickListener(v -> {
            String Title = etTitle.getText().toString();
            String Text = etText.getText().toString();

            if (Text.replace(" ", "").replace("\r", "").replace("\n", "").isEmpty()) {
                Toast.makeText(this, "Нечего сохранять", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                boolean isNewNote = (note == null);

                if (isNewNote) {
                    note = new Note();
                }

                note.title = Title.isEmpty() ? "Без заголовка" : Title;
                note.text = Text;
                note.date = tvDate.getText().toString().replace("Отредактировано: ", "");
                note.color = "#FFFFFF";

                if (isNewNote) {
                    NotesContext.Save(note, false);
                    Toast.makeText(this, "Заметка сохранена", Toast.LENGTH_SHORT).show();
                } else {
                    NotesContext.Save(note, true);
                    Toast.makeText(this, "Заметка обновлена", Toast.LENGTH_SHORT).show();
                }
            }
            finish();
        });

        bthTrash.setOnClickListener(v -> {
            RepoNotes.Notes.remove(note);
            finish();
            Toast.makeText(this, "Заметка удалена", Toast.LENGTH_SHORT).show();
        });
    }
}