package org.aaron.easycontent.mock;

/**
 * User: aprobus
 * Date: 2/17/13
 * Time: 12:28 PM
 */
public class Person {

   public Long id;
   public String firstName;
   public String lastName;
   public int age;

   public Person(String firstName, String lastName, int age) {
      id = null;
      this.firstName = firstName;
      this.lastName = lastName;
      this.age = age;
   }

   public Person() {

   }

}
