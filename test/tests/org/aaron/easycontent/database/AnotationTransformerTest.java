package org.aaron.easycontent.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.test.ProviderTestCase2;
import junit.framework.Assert;
import org.aaron.easycontent.mock.Animal;
import org.aaron.easycontent.mock.AnimalTable;
import org.aaron.easycontent.mock.PersonProvider;

/**
 * User: aprobus
 * Date: 2/18/13
 * Time: 7:16 PM
 */
public class AnotationTransformerTest extends ProviderTestCase2<PersonProvider> {

   private AnnotationTransformer<Animal> mAnimalTransformer = new AnnotationTransformer<Animal>(Animal.class);

   public AnotationTransformerTest() {
      super(PersonProvider.class, PersonProvider.AUTHORITY);
   }

   public void testToContentValues() {
      Animal animal = new Animal("Bobby", 24, false);
      animal.setId(24L);

      ContentValues values = mAnimalTransformer.toContentValues(animal);
      Assert.assertNotNull(values);

      Assert.assertEquals(animal.getId(), values.getAsLong(AnimalTable.COLUMN_ID));
      Assert.assertEquals(animal.getName(), values.getAsString(AnimalTable.COLUMN_NAME));
      Assert.assertEquals((short)0, values.getAsShort(AnimalTable.COLUMN_IS_ALIVE).shortValue());
      Assert.assertEquals(animal.getAge(), values.getAsInteger(AnimalTable.COLUMN_AGE));
   }

   public void testFromCursor() {
      Animal animal = new Animal("Bobby", 24, false);
      Uri animalUri = getMockContentResolver().insert(PersonProvider.URI_ANIMAL, mAnimalTransformer.toContentValues(animal));

      Cursor cursor = null;
      try {
         cursor = getMockContentResolver().query(animalUri, AnimalTable.COLUMNS, null, null, null);

         Assert.assertTrue(cursor.moveToNext());

         Animal animalFromCursor = mAnimalTransformer.fromCursor(cursor);
         Assert.assertNotNull(animalFromCursor);

         Assert.assertEquals(animal.getName(), animalFromCursor.getName());
         Assert.assertEquals(animal.getAlive(), animalFromCursor.getAlive());
         Assert.assertEquals(animal.getAge(), animalFromCursor.getAge());
      } finally {
         if (cursor != null) {
            cursor.close();
         }
      }
   }

}
