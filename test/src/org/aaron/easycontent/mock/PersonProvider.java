package org.aaron.easycontent.mock;

import android.net.Uri;
import org.aaron.easycontent.database.DatabaseTable;
import org.aaron.easycontent.provider.EasyContentProvider;

/**
 * User: aprobus
 * Date: 2/17/13
 * Time: 12:36 PM
 */
public class PersonProvider extends EasyContentProvider {

   public static final String AUTHORITY = "org.aaron.easycontent";
   private static final String DATABASE_NAME = "mock.db";
   private static final int DATABASE_VERSION = 1;

   public static final DatabaseTable[] DATABASE_TABLES = new DatabaseTable[]{ new PersonTable(), new LocationTable(), new AnimalTable() };

   private static final Uri URI_BASE = Uri.parse("content://" + AUTHORITY);
   public static final Uri URI_PERSON = Uri.withAppendedPath(URI_BASE, PersonTable.TABLE_NAME);
   public static final Uri URI_LOCATION = Uri.withAppendedPath(URI_BASE, LocationTable.TABLE_NAME);
   public static final Uri URI_ANIMAL = Uri.withAppendedPath(URI_BASE, AnimalTable.TABLE_NAME);

   public PersonProvider() {
      super(DATABASE_TABLES, AUTHORITY, DATABASE_NAME, DATABASE_VERSION);
   }

}
