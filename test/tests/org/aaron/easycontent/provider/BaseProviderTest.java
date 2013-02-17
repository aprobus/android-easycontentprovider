package org.aaron.easycontent.provider;

import android.content.ContentUris;
import android.net.Uri;
import android.test.ProviderTestCase2;
import junit.framework.Assert;
import org.aaron.easycontent.mock.Person;
import org.aaron.easycontent.mock.PersonFactory;
import org.aaron.easycontent.mock.PersonProvider;

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
      Person person = new Person();
      person.firstName = "First";
      person.lastName = "Last";
      person.age = 24;

      Uri personRowUri = getMockContentResolver().insert(PersonProvider.URI_PERSON, mPersonFactory.toContentValues(person));
      long personRowId = ContentUris.parseId(personRowUri);

      Assert.assertTrue(personRowId >= 0);
   }

}
