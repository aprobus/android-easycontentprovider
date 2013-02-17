package org.aaron.easycontent.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * User: aprobus
 * Date: 2/16/13
 * Time: 12:12 PM
 */
public interface DatabaseTable {
   public void onCreate(SQLiteDatabase db);
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
}
