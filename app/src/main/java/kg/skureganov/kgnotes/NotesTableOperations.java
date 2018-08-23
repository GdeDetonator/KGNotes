package kg.skureganov.kgnotes;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class NotesTableOperations {

    public static ArrayList<String> getNotesListFromDB(SQLiteDatabase database){
        ArrayList<String> notesList = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT title FROM notes;", null);
        int noteIdx = cursor.getColumnIndex("title");
        while (cursor.moveToNext()){
            notesList.add(cursor.getString(noteIdx));
        }
        cursor.close();
        return notesList;
    }

    public static String getNoteTextFromDB(SQLiteDatabase database, String title){
        Cursor cursor = database.rawQuery("SELECT text FROM notes WHERE title = '" + title + "';", null);
        int textIdx = cursor.getColumnIndex("text");
        StringBuilder text = new StringBuilder();
        while (cursor.moveToNext()){
            text.append(cursor.getString(textIdx));
        }
        cursor.close();
        return String.valueOf(text);
    }

    public static void deleteNoteFromDB(SQLiteDatabase database, String title){
        database.execSQL("DELETE FROM notes WHERE title = '" + title + "';");
    }

    public static void updateNoteInDB(SQLiteDatabase database, String title, String text){
        database.execSQL("UPDATE notes SET text = '" + text +"' WHERE title = '" + title + "';");
    }

    public static void insertNewNoteIntoDB(SQLiteDatabase database, String title, String text){
        database.execSQL("INSERT INTO notes (title, text) VALUES ('" + title + "', '" + text + "');");
    }

}
