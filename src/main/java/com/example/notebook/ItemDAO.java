package com.example.notebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by john on 11/28/15.
 */
public class ItemDAO {
    private SQLiteDatabase db;
    public static final String TABLE_NAME = "item";
    public static final String KEY_ID = "_id";
    public static final String TITLE_COLUMN = "title";
    public static final String CONTENT_COLUMN = "content";
    public static final String DATETIME_COLUMN = "datetime";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TITLE_COLUMN + " INTEGER NOT NULL, " +
                    CONTENT_COLUMN + " INTEGER NOT NULL, " +
                    DATETIME_COLUMN + " INTEGER NOT NULL )";

    public ItemDAO(Context context) {
        db = DBHelper.getDatabase(context);
    }

    public void close() {
        db.close();
    }

    public Item insert(Item item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE_COLUMN, item.getTitle());
        contentValues.put(CONTENT_COLUMN, item.getContent());
        contentValues.put(DATETIME_COLUMN, item.getDatetime());

        long id = db.insert(TABLE_NAME, null, contentValues);
        item.setId(id);
        return item;
    }

    public boolean update(Item item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE_COLUMN, item.getTitle());
        contentValues.put(CONTENT_COLUMN, item.getContent());
        contentValues.put(DATETIME_COLUMN, item.getDatetime());
        String where = KEY_ID + "=" + item.getId();
        return db.update(TABLE_NAME, contentValues, where, null) > 0;
    }

    public boolean delete(long id) {
        String where = KEY_ID + "=" + id;
        return db.delete(TABLE_NAME, where, null) > 0;
    }

    public List<Item> getAll() {
        List<Item> result = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }
        cursor.close();
        return result;
    }

    public Item get(long id) {
        Item item = null;
        String where = KEY_ID + "=" + id;
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null
        );

        if (result.moveToFirst()) {
            item = getRecord(result);
        }

        result.close();
        return item;
    }

    public Item getRecord(Cursor cursor) {
        Item result = new Item();
        result.setId(cursor.getLong(0));
        result.setTitle(cursor.getString(1));
        result.setContent(cursor.getString(2));
        result.setDatetime(cursor.getLong(3));

        return result;
    }

    public int getCount() {
        int  result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);
        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }
        return result;
    }

    public void sample() {
        Item item = new Item(0, "This is title", "Hello World!", new Date().getTime());
        insert(item);
    }
}
