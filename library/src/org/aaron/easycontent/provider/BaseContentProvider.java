package org.aaron.easycontent.provider;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;
import org.aaron.easycontent.database.DatabaseTable;

/**
 * User: aprobus
 * Date: 2/16/13
 * Time: 12:28 PM
 */
public abstract class BaseContentProvider extends ContentProvider {

   private DatabaseTable[] mTables;
   private String mAuthority;
   private String mDatabaseName;
   private Uri mBaseUri;
   private UriMatcher mUriMatcher;
   private SQLiteOpenHelper mDatabaseHelper;
   private int mDatabaseVersion;

   public BaseContentProvider(DatabaseTable[] tables, String authority, String databaseName, int dbVersion) {
      mTables = tables;
      mAuthority = authority;
      mDatabaseName = databaseName;
      mDatabaseVersion = dbVersion;
      mBaseUri = Uri.parse("content://" + authority);

      setupUris();
   }

   private void setupUris() {
      mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

      String authority = mAuthority;

      for (int i = 0; i < mTables.length; i++) {
         ContentProviderEndPoint endPoint = mTables[i].getClass().getAnnotation(ContentProviderEndPoint.class);
         mUriMatcher.addURI(authority, endPoint.tableName(), i);
         mUriMatcher.addURI(authority, endPoint.tableName() + "/#", i + 1);
      }
   }

   private DatabaseTable getTableForUri(Uri uri) {
      int matchResult = mUriMatcher.match(uri);

      if (matchResult < 0 || matchResult / 2 >= mTables.length) {
         throw new IllegalArgumentException("Invalid uri: " + uri);
      }

      return mTables[matchResult / 2];
   }

   @Override
   public boolean onCreate() {
      mDatabaseHelper = new BaseSQLiteOpenHelper(getContext());
      return true;
   }

   @Override
   public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
      DatabaseTable matchedTable = getTableForUri(uri);
      ContentProviderEndPoint tableMeta = matchedTable.getClass().getAnnotation(ContentProviderEndPoint.class);

      int matchResult = mUriMatcher.match(uri);
      boolean isDirectory = (matchResult & 1) == 0;

      SQLiteDatabase readableDb = mDatabaseHelper.getReadableDatabase();

      if (isDirectory) {
         return readableDb.query(tableMeta.tableName(), projection, selection, selectionArgs, null, null, sortOrder);
      } else {
         long rowId = ContentUris.parseId(uri);
         return readableDb.query(tableMeta.tableName(), projection, "_id = ?", new String[]{ Long.toString(rowId) }, null, null, sortOrder);
      }
   }

   @Override
   public String getType(Uri uri) {
      DatabaseTable matchedTable = getTableForUri(uri);

      int matchResult = mUriMatcher.match(uri);
      boolean isDirectory = (matchResult & 1) == 0;

      ContentProviderEndPoint tableMeta = matchedTable.getClass().getAnnotation(ContentProviderEndPoint.class);
      String supportedClass = tableMeta.mappedClass().getCanonicalName();

      if (isDirectory) {
         return ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + supportedClass;
      } else {
         return ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + supportedClass;
      }
   }

   @Override
   public Uri insert(Uri uri, ContentValues values) {
      DatabaseTable matchedTable = getTableForUri(uri);

      ContentProviderEndPoint tableMeta = matchedTable.getClass().getAnnotation(ContentProviderEndPoint.class);

      SQLiteDatabase writableDb = mDatabaseHelper.getWritableDatabase();
      long rowId = writableDb.insert(tableMeta.tableName(), null, values);

      Uri rowUri = ContentUris.withAppendedId(uri, rowId);

      getContext().getContentResolver().notifyChange(uri, null);

      return rowUri;
   }

   @Override
   public int delete(Uri uri, String selection, String[] selectionArgs) {
      DatabaseTable matchedTable = getTableForUri(uri);

      ContentProviderEndPoint tableMeta = matchedTable.getClass().getAnnotation(ContentProviderEndPoint.class);

      SQLiteDatabase writableDb = mDatabaseHelper.getWritableDatabase();
      int numRowsDeleted = writableDb.delete(tableMeta.tableName(), selection, selectionArgs);
      getContext().getContentResolver().notifyChange(uri, null);

      return numRowsDeleted;
   }

   @Override
   public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
      DatabaseTable matchedTable = getTableForUri(uri);

      ContentProviderEndPoint tableMeta = matchedTable.getClass().getAnnotation(ContentProviderEndPoint.class);

      SQLiteDatabase writableDb = mDatabaseHelper.getWritableDatabase();
      int numRowsUpdated = writableDb.update(tableMeta.tableName(), values, selection, selectionArgs);

      getContext().getContentResolver().notifyChange(uri, null);

      return numRowsUpdated;
   }

   @Override
   public void shutdown() {
      super.shutdown();
      mDatabaseHelper.close();
   }

   private class BaseSQLiteOpenHelper extends SQLiteOpenHelper {

      public BaseSQLiteOpenHelper(Context context) {
         super(context, mDatabaseName, null, mDatabaseVersion);
      }

      @Override
      public void onCreate(SQLiteDatabase db) {
         for (DatabaseTable table : mTables) {
            table.onCreate(db);
         }
      }

      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         for (DatabaseTable table : mTables) {
            table.onUpgrade(db, oldVersion, newVersion);
         }
      }
   }
}
