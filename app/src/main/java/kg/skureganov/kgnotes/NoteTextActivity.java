package kg.skureganov.kgnotes;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class NoteTextActivity extends AppCompatActivity {

    private String title;
    private Intent intent;
    private EditText noteText;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_text);
        intent = getIntent();
        title =  intent.getStringExtra(MainActivity.NOTE_TITLE);
        setTitle(title);

        database = new DataBaseHelper(getApplicationContext()).getWritableDatabase();

        noteText = findViewById(R.id.noteText);
        noteText.setText(NotesTableOperations.getNoteTextFromDB(database, title));
    }

    @Override
    protected void onStop() {
        super.onStop();
        NotesTableOperations.updateNoteInDB(database, title, noteText.getText().toString());
    }
}
