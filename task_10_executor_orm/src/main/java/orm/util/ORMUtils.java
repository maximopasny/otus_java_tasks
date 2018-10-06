package orm.util;

import orm.base.DataSet;
import orm.model.TypesSupported;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ORMUtils {
    public static List<Field> getAllFieldsSupported(Class<?> type) {
        List<Field> fields = new ArrayList<>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            Arrays.stream(c.getDeclaredFields())
                    .filter(field -> TypesSupported.classesSupported().contains(field.getType()))
                    .filter(field -> field.isAnnotationPresent(Column.class))
                    .forEach(fields::add);
        }
        return fields;
    }

    public static List<Field> getAllFieldsSupportedWithoutId(Class<?> type) {
        List<Field> fields = new ArrayList<>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            Arrays.stream(c.getDeclaredFields())
                    .filter(field -> TypesSupported.classesSupported().contains(field.getType()))
                    .filter(field -> field.isAnnotationPresent(Column.class))
                    .filter(field -> !field.isAnnotationPresent(Id.class))
                    .forEach(fields::add);
        }
        return fields;
    }

    public static String getIdFieldName(Class<?> type) {
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            Optional<Field> id = Arrays.stream(c.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(Id.class))
                    .findAny();

            if (id.isPresent()) {
                return id.get().getName();
            }
        }

        throw new IllegalStateException();
    }

    public static void setId(Object entity, Long id) throws InvocationTargetException, IllegalAccessException {
        Class type = entity.getClass();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            Optional<Field> idField = Arrays.stream(c.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(Id.class))
                    .findAny();

            if (idField.isPresent()) {
                Class<?> finalC = c;
                Optional<Method> setter = getSetterOptional(c, idField.get());
                if (setter.isPresent()) {
                    setter.get().invoke(entity, id);
                    return;
                }
            }
        }

        throw new IllegalStateException();
    }

    public static <T extends DataSet> T fillQueryResult(Class<T> clazz, ResultSet queryResult) throws Exception {
        Object result = null;

        try {
            result = clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }


        for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
            List<Field> fields = getAllFieldsSupported(clazz);

            for (Field field : fields) {
                Object columnValue = queryResult.getObject(ORMUtils.getColumn(field), field.getType());
                Optional<Method> setter = getSetterOptional(clazz, field);
                if (setter.isPresent()) {
                    setter.get().invoke(result, columnValue);
                } else {
                    throw new RuntimeException();
                }
            }
        }

        return (T) result;
    }

    public static String getColumn(Field field) {
        if (!field.isAnnotationPresent(Column.class)) {
            throw new IllegalStateException();
        }

        Column column = field.getAnnotation(Column.class);
        String columnName = column.name();
        return columnName;
    }

    public static String getTable(Class<?> type) {
        if (!type.isAnnotationPresent(Table.class)) {
            throw new IllegalStateException();
        }

        Table table = type.getAnnotation(Table.class);
        String tableName = table.name();
        return tableName;
    }

    private static Optional<Method> getSetterOptional(Class clazz, Field field) {
        Optional<Method> setter = Stream.of(clazz.getMethods())
                .filter(method -> method.getName().toLowerCase().equals("set" + field.getName())).findAny();
        return setter;
    }
}
