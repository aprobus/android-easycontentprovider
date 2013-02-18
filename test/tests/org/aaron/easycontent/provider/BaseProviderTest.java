package org.aaron.easycontent.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.test.ProviderTestCase2;
import junit.framework.Assert;
import org.aaron.easycontent.mock.*;

/**
 * User: aprobus
 * Date: 2/17/13
 * Time: 12:45 PM
 */
public class BaseProviderTest extends ProviderTestCase2<PersonProvider> {

   private PersonFactory mPersonFactory = new PersonFactory();

   public BaseProviderTest() {
      super(PersonProvider.class, PersonProvider.AUTHORITY);
   }

   public void testInsert() {
      Person person = new Person("First", "Last", 24);

      Uri personRowUri = getMockContentResolver().insert(PersonProvider.URI_PERSON, mPersonFactory.toContentValues(person));
      long personRowId = ContentUris.parseId(personRowUri);

      Assert.assertTrue(personRowId >= 0);
   }

   public void testQueryUri() {
      Person person = new Person("First", "Last", 24);
      Uri personRowUri = getMockContentResolver().insert(PersonProvider.URI_PERSON, mPersonFactory.toContentValues(person));

      Uri personRowUri2 = insertPerson("Fake", "Person", 16);

      Assert.assertNotSame(ContentUris.parseId(personRowUri), ContentUris.parseId(personRowUri2));

      Cursor personCursor = null;
      try {
         personCursor = getMockContentResolver().query(personRowUri, PersonTable.COLUMNS, null, null, null);

         Assert.assertEquals(1, personCursor.getCount());

         Assert.assertTrue(personCursor.moveToNext());

         Person personFromCursor = mPersonFactory.fromCursor(personCursor);

         Assert.assertEquals(person.firstName, personFromCursor.firstName);
         Assert.assertEquals(person.lastName, personFromCursor.lastName);
         Assert.assertEquals(person.age, personFromCursor.age);

         Assert.assertFalse(personCursor.moveToNext());
      } finally {
         if (personCursor != null) {
            personCursor.close();
         }
      }
   }

   public void testQueryWhere() {
      Uri personUri = insertPerson("Ron", "Burgandy", 32);
      insertPerson("Al", "Capone", 40);

      long personRowId = ContentUris.parseId(personUri);

      Cursor personCursor = null;
      try {
         personCursor = getMockContentResolver().query(PersonProvider.URI_PERSON, PersonTable.COLUMNS, PersonTable.COLUMN_ID + " = ?", new String[]{ Long.toString(personRowId) }, null);

         Assert.assertTrue(personCursor.moveToNext());
         Person personFromCursor = mPersonFactory.fromCursor(personCursor);

         Assert.assertEquals("Ron", personFromCursor.firstName);
         Assert.assertEquals("Burgandy", personFromCursor.lastName);
         Assert.assertEquals(32, personFromCursor.age);

         Assert.assertFalse(personCursor.moveToNext());
      } finally {
         if (personCursor != null) {
            personCursor.close();
         }
      }
   }

   public void testDeleteWhere() {
      Uri person1RowUri = insertPerson("Ron", "Burgandy", 32);

      Uri person2RowUri = insertPerson("Al", "Capone", 40);
      long person2Id = ContentUris.parseId(person2RowUri);

      Cursor personCursor = null;
      try {
         int numRowsDeleted = getMockContentResolver().delete(PersonProvider.URI_PERSON, PersonTable.COLUMN_ID + " = ?", new String[]{ Long.toString(person2Id) });

         Assert.assertEquals(1, numRowsDeleted);

         personCursor = getMockContentResolver().query(person1RowUri, PersonTable.COLUMNS, null, null, null);
         Assert.assertEquals(1, personCursor.getCount());
         personCursor.close();

         personCursor = getMockContentResolver().query(person2RowUri, PersonTable.COLUMNS, null, null, null);
         Assert.assertEquals(0, personCursor.getCount());
      } finally {
         if (personCursor != null) {
            personCursor.close();
         }
      }
   }

   public void testDeleteUri() {
      Uri person1RowUri = insertPerson("Ron", "Burgandy", 32);
      Uri person2RowUri = insertPerson("Al", "Capone", 40);

      Cursor personCursor = null;
      try {
         int numRowsDeleted = getMockContentResolver().delete(person2RowUri, null, null);

         Assert.assertEquals(1, numRowsDeleted);

         personCursor = getMockContentResolver().query(person1RowUri, PersonTable.COLUMNS, null, null, null);
         Assert.assertEquals(1, personCursor.getCount());
         personCursor.close();

         personCursor = getMockContentResolver().query(person2RowUri, PersonTable.COLUMNS, null, null, null);
         Assert.assertEquals(0, personCursor.getCount());
      } finally {
         if (personCursor != null) {
            personCursor.close();
         }
      }
   }

   public void testUpdateUri() {
      Person person = new Person("First", "Last", 3);
      Uri personRowUri = getMockContentResolver().insert(PersonProvider.URI_PERSON, mPersonFactory.toContentValues(person));

      insertPerson("Fake", "Person", 10);

      person.firstName = "test";
      getMockContentResolver().update(personRowUri, mPersonFactory.toContentValues(person), null, null);

      Cursor personCursor = null;
      try {
         personCursor = getMockContentResolver().query(PersonProvider.URI_PERSON, PersonTable.COLUMNS, null, null, PersonTable.COLUMN_AGE + " asc");

         Assert.assertTrue(personCursor.moveToNext());

         Person personFromCursor = mPersonFactory.fromCursor(personCursor);

         Assert.assertEquals(person.firstName, personFromCursor.firstName);
         Assert.assertEquals(person.lastName, personFromCursor.lastName);
         Assert.assertEquals(person.age, personFromCursor.age);

         Assert.assertTrue(personCursor.moveToNext());

         personFromCursor = mPersonFactory.fromCursor(personCursor);
         Assert.assertEquals("Fake", personFromCursor.firstName);
         Assert.assertEquals("Person", personFromCursor.lastName);
         Assert.assertEquals(10, personFromCursor.age);
      } finally {
         if (personCursor != null) {
            personCursor.close();
         }
      }
   }

   public void testUpdateWhere() {
      Person person = new Person("First", "Last", 3);
      Uri personRowUri = getMockContentResolver().insert(PersonProvider.URI_PERSON, mPersonFactory.toContentValues(person));
      long personId = ContentUris.parseId(personRowUri);

      insertPerson("Fake", "Person", 10);

      person.firstName = "test";
      getMockContentResolver().update(PersonProvider.URI_PERSON, mPersonFactory.toContentValues(person), PersonTable.COLUMN_ID + " = ?", new String[]{ Long.toString(personId) });

      Cursor personCursor = null;
      try {
         personCursor = getMockContentResolver().query(PersonProvider.URI_PERSON, PersonTable.COLUMNS, null, null, PersonTable.COLUMN_AGE + " asc");

         Assert.assertTrue(personCursor.moveToNext());

         Person personFromCursor = mPersonFactory.fromCursor(personCursor);

         Assert.assertEquals(person.firstName, personFromCursor.firstName);
         Assert.assertEquals(person.lastName, personFromCursor.lastName);
         Assert.assertEquals(person.age, personFromCursor.age);

         Assert.assertTrue(personCursor.moveToNext());

         personFromCursor = mPersonFactory.fromCursor(personCursor);
         Assert.assertEquals("Fake", personFromCursor.firstName);
         Assert.assertEquals("Person", personFromCursor.lastName);
         Assert.assertEquals(10, personFromCursor.age);
      } finally {
         if (personCursor != null) {
            personCursor.close();
         }
      }
   }

   public void testGetTypeMultiple() {
      String extectedType = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + Person.class.getCanonicalName();
      String actualType = getMockContentResolver().getType(PersonProvider.URI_PERSON);

      Assert.assertEquals(extectedType, actualType);
   }

   public void testGetTypeSingle() {
      String extectedType = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + Person.class.getCanonicalName();
      String actualType = getMockContentResolver().getType(ContentUris.withAppendedId(PersonProvider.URI_PERSON, 1));

      Assert.assertEquals(extectedType, actualType);
   }

   public void testGetTypeMultipleLocation() {
      String extectedType = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + Location.class.getCanonicalName();
      String actualType = getMockContentResolver().getType(PersonProvider.URI_LOCATION);

      Assert.assertEquals(extectedType, actualType);
   }

   public void testGetTypeSingleLocation() {
      String extectedType = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + Location.class.getCanonicalName();
      String actualType = getMockContentResolver().getType(ContentUris.withAppendedId(PersonProvider.URI_LOCATION, 1));

      Assert.assertEquals(extectedType, actualType);
   }

   private Uri insertPerson(String firstName, String lastName, int age) {
      Person person = new Person(firstName, lastName, age);

      return getMockContentResolver().insert(PersonProvider.URI_PERSON, mPersonFactory.toContentValues(person));
   }

}
