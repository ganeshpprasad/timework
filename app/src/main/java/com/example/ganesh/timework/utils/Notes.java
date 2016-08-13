package com.example.ganesh.timework.utils;

import android.database.Cursor;

import com.example.ganesh.timework.data.DatabaseContract.NotesContract;

/**
 * Created by Ganesh Prasad on 28-07-2016.
 */
public class Notes {

    private String name;
    private String content;
    private String type;
    private int createdHour;
    private int createdMinutes;
    private int createdDate;
    private int createdMonth;

    private int id;

    public Notes(int _id, String _name, String _type, String _content) {

        this.id = _id;
        this.name = _name;
        this.type = _type;
        this.content = _content;

    }

    public void setCreatedHour(int createdHour) {
        this.createdHour = createdHour;
    }

    public void setCreatedMinutes(int createdMinutes) {
        this.createdMinutes = createdMinutes;
    }

    public void setCreatedDate(int createdDate) {
        this.createdDate = createdDate;
    }

    public void setCreatedMonth(int createdMonth) {
        this.createdMonth = createdMonth;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public int getCreatedHour() {
        return createdHour;
    }

    public int getCreatedMinutes() {
        return createdMinutes;
    }

    public int getCreatedDate() {
        return createdDate;
    }

    public int getCreatedMonth() {
        return createdMonth;
    }

    public int getId() {
        return id;
    }

    //    method to convert cursor into a Notes item / object
    public static Notes getNotesFromCursor(Cursor cursor) {

        Notes notes = null;

        if (cursor != null) {

            if (cursor.moveToNext()) {

                int id = cursor.getInt(cursor.getColumnIndexOrThrow(NotesContract._ID));

                String notesName = cursor.getString(cursor.getColumnIndexOrThrow(NotesContract.COLUMN_NOTES_NAME));
                String notesContent = cursor.getString(cursor.getColumnIndexOrThrow(NotesContract.COLUMN_NOTES_CONTENT));
                String type = cursor.getString(cursor.getColumnIndexOrThrow(NotesContract.COLUMN_NOTES_TYPE));

                int hour = cursor.getInt(cursor.getColumnIndexOrThrow(NotesContract.COLUMN_NOTES_CREATED_HOUR));
                int minutes = cursor.getInt(cursor.getColumnIndexOrThrow(NotesContract.COLUMN_NOTES_CREATED_MINUTES));
                int date = cursor.getInt(cursor.getColumnIndexOrThrow(NotesContract.COLUMN_NOTES_CREATED_DATE));
                int month = cursor.getInt(cursor.getColumnIndexOrThrow(NotesContract.COLUMN_NOTES_CREATED_MONTH));

                notes = new Notes(id, notesName, type, notesContent);
                notes.setCreatedHour(hour);
                notes.setCreatedMinutes(minutes);
                notes.setCreatedDate(date);
                notes.setCreatedMonth(month);

            }
        }
        return notes;
    }

}
