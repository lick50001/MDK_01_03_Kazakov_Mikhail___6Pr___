package com.example.notes_kazakov.presentations;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.notes_kazakov.R;
import com.example.notes_kazakov.datas.RepoNotes;
import com.example.notes_kazakov.domains.models.Note;
import java.text.SimpleDateFormat;
import java.util.Date;

/* Activity для создания и редактирования заметок
 * Позволяет просматривать, редактировать, создавать и удалять заметки */
public class NoteActivity extends AppCompatActivity {

    // Текущая редактируемая заметка (null если создается новая)
    Note note;

    // UI элементы
    EditText etTitle, etText;       // Поля ввода заголовка и текста заметки
    TextView tvDate;                // Отображение даты последнего редактирования
    View bthSelectColor, bthBack, bthTrash; // Кнопки: выбор цвета, назад, удалить

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        // Получение текущей даты и времени для отображения времени редактирования
        Date DateNow = new Date();
        // Форматирование даты в формат "часы:минуты:секунды день.месяц.год"
        SimpleDateFormat FormatForDateNow = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");

        // Инициализация UI элементов по их ID из layout
        bthSelectColor = findViewById(R.id.bth_select_color);
        bthBack = findViewById(R.id.bth_back);
        bthTrash = findViewById(R.id.bth_trash);
        etTitle = findViewById(R.id.et_title);
        etText = findViewById(R.id.et_text);
        tvDate = findViewById(R.id.tv_date);

        // Получение переданных аргументов из Intent (из NotesActivity)
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            // Если есть аргументы, значит открыта существующая заметка для редактирования
            int Position = arguments.getInt("position");  // Получаем позицию заметки в списке

            // Получаем заметку из репозитория по позиции
            note = RepoNotes.Notes.get(Position);

            // Заполняем поля ввода данными из заметки
            etTitle.setText(note.title);
            etText.setText(note.text);
        } else {
            // Если аргументов нет - создается новая заметка, скрываем кнопку удаления
            bthTrash.setVisibility(View.GONE);
        }
        // Устанавливаем дату редактирования (всегда текущая дата при открытии)
        tvDate.setText("Отредактировано: " + FormatForDateNow.format(DateNow));

        /**
         * Обработчик нажатия на кнопку выбора цвета
         * В текущей реализации показывает сообщение о недоступности функции
         */
        bthSelectColor.setOnClickListener(v -> {
            Toast.makeText(this, "Выбор цвета недоступен", Toast.LENGTH_SHORT).show();
        });

        /**
         * Обработчик нажатия на кнопку "Назад" (сохранение и выход)
         * Сохраняет изменения в заметке и закрывает Activity
         */
        bthBack.setOnClickListener(v -> {
            // Получаем введенный текст из полей
            String Title = etTitle.getText().toString();
            String Text = etText.getText().toString();

            // Проверка: если текст заметки пустой (содержит только пробелы и переносы строк)
            if (Text
                    .replace(" ", "")      // Удаляем пробелы
                    .replace("\r", "")     // Удаляем возврат каретки
                    .replace("\n", "")     // Удаляем перенос строки
                    .isEmpty()) {
                // Если после очистки текст пуст - показываем предупреждение и не сохраняем
                Toast.makeText(this, "Не чего сохранять", Toast.LENGTH_SHORT).show();
            } else {
                // Если текст не пустой - сохраняем заметку
                if (note == null) {
                    // Если заметка новая (note == null) - создаем объект и добавляем в репозиторий
                    note = new Note();
                    RepoNotes.Notes.add(note);
                }

                // Заполняем заметку данными
                note.title = Title;                // Сохраняем заголовок
                note.text = Text;                  // Сохраняем текст
                note.date = FormatForDateNow.format(DateNow); // Сохраняем дату редактирования
            }

            // Закрываем текущую Activity и возвращаемся к списку заметок
            finish();
        });

        /**
         * Обработчик нажатия на кнопку "Удалить"
         * Удаляет текущую заметку из репозитория и закрывает Activity
         */
        bthTrash.setOnClickListener(v -> {
            // Удаляем заметку из списка репозитория
            RepoNotes.Notes.remove(note);
            // Закрываем Activity
            finish();
            // Показываем уведомление об успешном удалении
            Toast.makeText(this, "Заметка удалена", Toast.LENGTH_SHORT).show();
        });
    }
}