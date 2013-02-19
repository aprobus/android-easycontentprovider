package org.aaron.easycontent.mock;

import org.aaron.easycontent.database.Column;
import org.aaron.easycontent.database.org.aaron.easycontent.valuetransformers.BoolTransformer;
import org.aaron.easycontent.database.org.aaron.easycontent.valuetransformers.IntTransformer;
import org.aaron.easycontent.database.org.aaron.easycontent.valuetransformers.LongTransformer;
import org.aaron.easycontent.database.org.aaron.easycontent.valuetransformers.StringTransformer;

/**
 * User: aprobus
 * Date: 2/18/13
 * Time: 7:07 PM
 */
public class Animal {

   @Column(columnName = AnimalTable.COLUMN_ID, transformer = LongTransformer.class)
   private Long id;

   @Column(columnName = AnimalTable.COLUMN_IS_ALIVE, transformer = BoolTransformer.class)
   private Boolean isAlive;

   @Column(columnName = AnimalTable.COLUMN_NAME, transformer = StringTransformer.class)
   private String name;

   @Column(columnName = AnimalTable.COLUMN_AGE, transformer = IntTransformer.class)
   private Integer age;

   public Animal() {

   }

   public Animal(String name, int age, boolean isAlive) {
      this.name = name;
      this.age = age;
      this.isAlive = isAlive;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Boolean getAlive() {
      return isAlive;
   }

   public void setAlive(Boolean alive) {
      isAlive = alive;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Integer getAge() {
      return age;
   }

   public void setAge(Integer age) {
      this.age = age;
   }
}
