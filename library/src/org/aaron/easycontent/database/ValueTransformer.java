package org.aaron.easycontent.database;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * User: aprobus
 * Date: 2/18/13
 * Time: 4:48 PM
 */
public interface ValueTransformer<T> {
   public void addValue(String columnName, ContentValues values, T obj);
   public T getValue(String columnName, Cursor cursor);
}
