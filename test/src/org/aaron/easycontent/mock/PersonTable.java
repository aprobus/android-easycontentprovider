package org.aaron.easycontent.mock;

import android.database.sqlite.SQLiteDatabase;
import org.aaron.easycontent.database.DatabaseTable;
import org.aaron.easycontent.provider.ContentProviderEndPoint;

/**
 * User: aprobus
 * Date: 2/17/13
 * Time: 12:29 PM
 */
@ContentProviderEndPoint(tableName = PersonTable.TABLE_NAME, mappedClass = Person.class)
public class PersonTable implements DatabaseTable {

   public static final String TABLE_NAME = "persons";

   public static final String COLUMN_ID = "_id";
   public static final String COLUMN_FIRST_NAME = "firstName";
   public static final String COLUMN_LAST_NAME = "lastName";
   public static final String COLUMN_AGE = "age";

   public static final String[] COLUMNS = new String[]{ COLUMN_ID, COLUMN_AGE, COLUMN_FIRST_NAME, COLUMN_LAST_NAME };

   @Override
   public void onCreate(SQLiteDatabase db) {
      StringBuilder createTableStatement = new StringBuilder();
      createTableStatement.append("create table " + TABLE_NAME);
      createTableStatement.append("(");

      createTableStatement.append(COLUMN_ID);
      createTableStatement.append(" integer primary key autoincrement, ");

      createTableStatement.append(COLUMN_FIRST_NAME);
      createTableStatement.append(" text not null, ");

      createTableStatement.append(COLUMN_LAST_NAME);
      createTableStatement.append(" not null, ");

      createTableStatement.append(COLUMN_AGE);
      createTableStatement.append(" integer");

      createTableStatement.append(")");

      db.execSQL(createTableStatement.toString());
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
   }
}
