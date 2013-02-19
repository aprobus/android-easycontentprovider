package org.aaron.easycontent.mock;

import android.database.sqlite.SQLiteDatabase;
import org.aaron.easycontent.database.DatabaseTable;
import org.aaron.easycontent.provider.ContentProviderTable;

/**
 * User: aprobus
 * Date: 2/18/13
 * Time: 7:08 PM
 */
@ContentProviderTable(tableName = AnimalTable.TABLE_NAME, mappedClass = Animal.class)
public class AnimalTable implements DatabaseTable {

   public static final String TABLE_NAME = "animals";

   public static final String COLUMN_ID = "_id";
   public static final String COLUMN_IS_ALIVE = "alive";
   public static final String COLUMN_AGE = "age";
   public static final String COLUMN_NAME = "name";

   public static final String[] COLUMNS = new String[]{ COLUMN_ID, COLUMN_AGE, COLUMN_IS_ALIVE, COLUMN_NAME };

   @Override
   public void onCreate(SQLiteDatabase db) {
      StringBuilder sql = new StringBuilder();

      sql.append("create table " + TABLE_NAME + "(");

      sql.append(COLUMN_ID);
      sql.append(" integer primary key autoincrement, ");

      sql.append(COLUMN_NAME);
      sql.append(" text, ");

      sql.append(COLUMN_AGE);
      sql.append(" integer, ");

      sql.append(COLUMN_IS_ALIVE);
      sql.append(" integer)");

      db.execSQL(sql.toString());
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
   }
}
