package org.aaron.easycontent.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import junit.framework.Assert;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: aprobus
 * Date: 2/18/13
 * Time: 3:54 PM
 */
public class AnnotationTransformer<T> implements RelationalTransformer<T> {

   private Class<T> mAnnotatedClass;
   private Map<Class<? extends ValueTransformer>, ValueTransformer> mTransformers;

   public AnnotationTransformer(Class<T> annotatedClass) {
      mTransformers = new HashMap<Class<? extends ValueTransformer>, ValueTransformer>();
      mAnnotatedClass = annotatedClass;
   }

   @Override
   public T fromCursor(Cursor cursor) {
      try {
         Constructor<T> defaultConstructor = mAnnotatedClass.getConstructor();
         T obj = defaultConstructor.newInstance();

         Field[] fields = mAnnotatedClass.getDeclaredFields();

         for (Field field : fields) {
            if (!field.isAnnotationPresent(Column.class)) {
               continue;
            }

            Column column = field.getAnnotation(Column.class);
            if (!mTransformers.containsKey(column.transformer())) {
               ValueTransformer transformer = column.transformer().getConstructor().newInstance();
               mTransformers.put(column.transformer(), transformer);
            }

            field.setAccessible(true);
            field.set(obj, mTransformers.get(column.transformer()).getValue(column.columnName(), cursor));
         }

         return obj;
      } catch (IllegalAccessException e) {
         
      } catch (NoSuchMethodException e) {

      } catch (InvocationTargetException e) {

      } catch (InstantiationException e) {

      }

      return null;
   }

   @Override
   public ContentValues toContentValues(T obj) {
      try {
         ContentValues values = new ContentValues();
         Field[] fields = mAnnotatedClass.getDeclaredFields();

         Assert.assertTrue(fields.length > 0);

         for (Field field : fields) {
            if (!field.isAnnotationPresent(Column.class)) {
               continue;
            }

            Column column = field.getAnnotation(Column.class);
            if (!mTransformers.containsKey(column.transformer())) {
               ValueTransformer transformer = column.transformer().getConstructor().newInstance();
               mTransformers.put(column.transformer(), transformer);
            }

            field.setAccessible(true);
            mTransformers.get(column.transformer()).addValue(column.columnName(), values, field.get(obj));
         }

         return values;
      } catch (NoSuchMethodException e) {

      } catch (InvocationTargetException e) {

      } catch (IllegalAccessException e) {

      } catch (InstantiationException e) {

      }

      return null;
   }
}
