package org.aaron.easycontent.database;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * User: aprobus
 * Date: 2/16/13
 * Time: 12:08 PM
 */
public interface RelationalTransformer<T> {
   public T fromCursor(Cursor cursor);
   public ContentValues toContentValues(T obj);
}
