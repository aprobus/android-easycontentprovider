package org.aaron.easycontent.provider;

import org.aaron.easycontent.database.DatabaseTable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User: aprobus
 * Date: 2/16/13
 * Time: 5:49 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface ContentProviderTable {
   String tableName();
   Class<?> mappedClass();
}
