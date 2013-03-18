package org.aaron.easycontent.mock;

import android.content.ContentValues;
import android.database.Cursor;
import org.aaron.easycontent.database.RowTransformer;

/**
 * User: aprobus
 * Date: 2/17/13
 * Time: 12:31 PM
 */
public class PersonFactory implements RowTransformer<Person> {

   @Override
   public Person fromCursor(Cursor cursor) {
      Person person = new Person();

      person.id = cursor.getLong(cursor.getColumnIndex(PersonTable.COLUMN_ID));
      person.firstName = cursor.getString(cursor.getColumnIndex(PersonTable.COLUMN_FIRST_NAME));
      person.lastName = cursor.getString(cursor.getColumnIndex(PersonTable.COLUMN_LAST_NAME));
      person.age = cursor.getInt(cursor.getColumnIndex(PersonTable.COLUMN_AGE));

      return person;
   }

   @Override
   public ContentValues toContentValues(Person obj) {
      ContentValues values = new ContentValues();
      values.put(PersonTable.COLUMN_ID, obj.id);
      values.put(PersonTable.COLUMN_FIRST_NAME, obj.firstName);
      values.put(PersonTable.COLUMN_LAST_NAME, obj.lastName);
      values.put(PersonTable.COLUMN_AGE, obj.age);

      return values;
   }
}
