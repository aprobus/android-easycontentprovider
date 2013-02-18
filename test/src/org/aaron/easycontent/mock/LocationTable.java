package org.aaron.easycontent.mock;

import android.database.sqlite.SQLiteDatabase;
import org.aaron.easycontent.database.DatabaseTable;
import org.aaron.easycontent.provider.ContentProviderEndPoint;

/**
 * User: aprobus
 * Date: 2/18/13
 * Time: 12:16 PM
 */
@ContentProviderEndPoint(tableName = LocationTable.TABLE_NAME, mappedClass = Location.class)
public class LocationTable implements DatabaseTable {
   public static final String TABLE_NAME = "locations";

   public static final String COLUMN_ID = "_id";
   public static final String COLUMN_ADDRESS = "address";
   public static final String COLUMN_CITY = "city";

   @Override
   public void onCreate(SQLiteDatabase db) {
      StringBuilder sql = new StringBuilder();
      sql.append("create table " + TABLE_NAME + "(");

      sql.append(COLUMN_ID);
      sql.append(" integer primary key autoincrement, ");

      sql.append(COLUMN_ADDRESS);
      sql.append(" text , ");

      sql.append(COLUMN_CITY);
      sql.append(" text)");

      db.execSQL(sql.toString());
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
   }
}
