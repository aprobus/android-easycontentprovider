package org.aaron.easycontent.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User: aprobus
 * Date: 2/18/13
 * Time: 4:07 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface Column {
   String columnName();
   Class<? extends ValueTransformer> transformer();
}
