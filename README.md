# Easy Content Provider

## Description
After working on several Android apps, I found that my ContentProvider implementation was nearly identical every time.
Easy Content Provider (ECP) is my solution to this problem. With the use of a few annotations, you can now get a
free ContentProvider!

## How to use ECP
ECP requires a few well structed classes to work.

For each table, create a class that extends DatabaseTable and has a ContentProviderTable annotation.

```java
//MappedClass is the class of objects held in the table
@ContentProviderTable(tableName = PeopleTable.TABLE_NAME, mappedClass = Person.class)
public class PeopleTable extends DatabaseTable {
    public static final String TABLE_NAME = "people";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FIRST_NAME = "firstName";
    //... Any additional columns

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Upgrade table
    }
}
```

You will also need a class to extend from EasyContentProvider. Also, remember to register your content provider in the
manifest.

```java

public class MyContentProvider extends EasyContentProvider {
    //Authority for your app, should be your default package
    public static final String AUTHORITY = "org.aaron.easycontent";

    //Database properties
    private static final String DATABASE_NAME = "mock.db";
    private static final int DATABASE_VERSION = 1;

    //All tables supported by content provider
    public static final DatabaseTable[] DATABASE_TABLES = new DatabaseTable[]{ new PersonTable(), new LocationTable() };

    //Uris for all your tables. They must follow the pattern of "content://[AUTHORITY]/[TABLE_NAME]"
    private static final Uri URI_BASE = Uri.parse("content://" + AUTHORITY);
    public static final Uri URI_PERSON = Uri.withAppendedPath(URI_BASE, PersonTable.TABLE_NAME);
    public static final Uri URI_LOCATION = Uri.withAppendedPath(URI_BASE, LocationTable.TABLE_NAME);

    public MyContentProvider() {
        super(DATABASE_TABLES, AUTHORITY, DATABASE_NAME, DATABASE_VERSION);
    }
}

```
