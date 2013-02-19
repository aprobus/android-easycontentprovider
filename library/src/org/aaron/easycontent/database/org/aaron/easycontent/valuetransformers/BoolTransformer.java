package org.aaron.easycontent.database.org.aaron.easycontent.valuetransformers;

import android.content.ContentValues;
import android.database.Cursor;
import org.aaron.easycontent.database.ValueTransformer;

/**
 * User: aprobus
 * Date: 2/18/13
 * Time: 5:12 PM
 */
public class BoolTransformer implements ValueTransformer<Boolean> {

   @Override
   public void addValue(String columnName, ContentValues values, Boolean obj) {
      if (obj == null) {
         values.putNull(columnName);
         return;
      }

      short booleanValue = obj ? (short)1 : (short)0;
      values.put(columnName, booleanValue);
   }

   @Override
   public Boolean getValue(String columnName, Cursor cursor) {
      int columnIndex = cursor.getColumnIndex(columnName);

      if (columnIndex < 0) {
         return null;
      } else if (cursor.isNull(columnIndex)) {
         return null;
      }

      return cursor.getShort(columnIndex) == 1;
   }
}
