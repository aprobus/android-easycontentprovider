package org.aaron.easycontent.database.org.aaron.easycontent.valuetransformers;

import android.content.ContentValues;
import android.database.Cursor;
import org.aaron.easycontent.database.ValueTransformer;

/**
 * User: aprobus
 * Date: 2/18/13
 * Time: 5:09 PM
 */
public class ShortTransformer implements ValueTransformer<Short> {

   @Override
   public void addValue(String columnName, ContentValues values, Short obj) {
      if (obj == null) {
         values.putNull(columnName);
         return;
      }

      values.put(columnName, obj.shortValue());
   }

   @Override
   public Short getValue(String columnName, Cursor cursor) {
      int columnIndex = cursor.getColumnIndex(columnName);

      if (columnIndex < 0) {
         return null;
      } else if (cursor.isNull(columnIndex)) {
         return null;
      }

      return cursor.getShort(columnIndex);
   }
}
