package kg.skureganov.kgnotes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;


import java.util.ArrayList;

import kg.skureganov.kgnotes.adapters.rvMainAdapter;

public class MainActivity extends AppCompatActivity {

    public static final String NOTE_TITLE = "note title";

    private SQLiteDatabase database;
    private RecyclerView rvMain;
    private rvMainAdapter rvMainAdapter;
    private AlertDialog.Builder alert;
    private ArrayList<String> notesList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDB();
        notesList = NotesTableOperations.getNotesListFromDB(database);
        initRvMain();


    }

    private void initRvMain() {

        rvMain = findViewById(R.id.rvMain);
        registerForContextMenu(rvMain);
        rvMain.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        rvMainAdapter = new rvMainAdapter(notesList, new rvMainAdapter.rvMainClickListenet() {
            @Override
            public void onRVMainClick(int position) {
                Intent intent = new Intent(MainActivity.this, NoteTextActivity.class);
                intent.putExtra(NOTE_TITLE, rvMainAdapter.getNoteTitle(position));
                startActivity(intent);
            }
        });
        rvMain.setAdapter(rvMainAdapter);
    }


    private void initDB(){
        database = new DataBaseHelper(getApplicationContext()).getWritableDatabase();
    }

    private void showAlertDialog(){
        final EditText editText = new EditText(getApplicationContext());
        alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.alertDialogTitle);
        alert.setView(editText);
        alert.setPositiveButton(R.string.alertDialogPositiveButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                NotesTableOperations.insertNewNoteIntoDB(database, editText.getText().toString(), "");
                notesList.add(editText.getText().toString());
                rvMainAdapter.notifyDataSetChanged();
            }
        });
        alert.setNegativeButton(R.string.alertDialogNegativeButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        alert.show();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menu.removeItem(R.id.menuDelete);
        menu.removeItem(R.id.menuEdit);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menuAdd){
            showAlertDialog();
            return true;
        }
        return false;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuDelete){
            NotesTableOperations.deleteNoteFromDB(database, notesList.get(rvMainAdapter.getRvMainItemPosition()));
            notesList.remove(rvMainAdapter.getRvMainItemPosition());
            rvMainAdapter.notifyDataSetChanged();
            return true;
        }

        return false;
    }
}
