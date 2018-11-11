package jettytask.orm.model;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TypesSupported {
    private static Map<Class, Integer> mappingToSQLTypes;

    static {
        mappingToSQLTypes = new HashMap<>();
        mappingToSQLTypes.put(Integer.class, Types.INTEGER);
        mappingToSQLTypes.put(Long.class, Types.BIGINT);
        mappingToSQLTypes.put(String.class, Types.VARCHAR);
    }

    public static Integer getSqlType(Class clazz) {
        if (!mappingToSQLTypes.containsKey(clazz)) {
            throw new IllegalArgumentException();
        }

        return mappingToSQLTypes.get(clazz);
    }

    public static Set<Class> classesSupported() {
        return mappingToSQLTypes.keySet();
    }
}
