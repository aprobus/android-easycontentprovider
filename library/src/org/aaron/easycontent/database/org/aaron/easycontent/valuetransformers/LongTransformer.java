package org.aaron.easycontent.database.org.aaron.easycontent.valuetransformers;

import android.content.ContentValues;
import android.database.Cursor;
import org.aaron.easycontent.database.ValueTransformer;

/**
 * User: aprobus
 * Date: 2/18/13
 * Time: 7:11 PM
 */
public class LongTransformer implements ValueTransformer<Long> {

   @Override
   public void addValue(String columnName, ContentValues values, Long obj) {
      if (obj == null) {
         values.putNull(columnName);
         return;
      }

      values.put(columnName, obj.longValue());
   }

   @Override
   public Long getValue(String columnName, Cursor cursor) {
      int columnIndex = cursor.getColumnIndex(columnName);

      if (columnIndex < 0) {
         return null;
      } else if (cursor.isNull(columnIndex)) {
         return null;
      }

      return cursor.getLong(columnIndex);
   }
}
