package org.aaron.easycontent.database.org.aaron.easycontent.valuetransformers;

import android.content.ContentValues;
import android.database.Cursor;
import org.aaron.easycontent.database.ValueTransformer;

/**
 * User: aprobus
 * Date: 2/18/13
 * Time: 4:53 PM
 */
public class IntTransformer implements ValueTransformer<Integer> {

   @Override
   public void addValue(String columnName, ContentValues values, Integer obj) {
      if (obj == null) {
         values.putNull(columnName);
         return;
      }

      values.put(columnName, obj.intValue());
   }

   @Override
   public Integer getValue(String columnName, Cursor cursor) {
      int columnIndex = cursor.getColumnIndex(columnName);

      if (columnIndex < 0) {
         return null;
      } else if (cursor.isNull(columnIndex)) {
         return null;
      }

      return cursor.getInt(columnIndex);
   }
}
