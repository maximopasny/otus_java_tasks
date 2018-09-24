package writer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Set;

public class JsonWriterImpl implements JsonWriter {

    private Set<Class> finiteClasses = Set.of(
            Boolean.class,
            Character.class,
            Byte.class,
            Short.class,
            Integer.class,
            Float.class,
            Double.class,
            String.class);

    @Override
    public String writeToJson(Object object) {
        if (isJsonLeaf(object.getClass())) {
            return "";
        }

        JSONObject jsonObject = objectToJsonObject(object);
        return jsonObject.toJSONString();
    }

    private JSONObject objectToJsonObject(Object object) {
        JSONObject jsonObject = new JSONObject();
        Field[] fields = object.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                if (java.lang.reflect.Modifier.isStatic(field.getModifiers()))
                    continue;
                field.setAccessible(true);
                Class<?> type = field.getType();

                if (field.get(object) == null) {
                    continue;
                }

                if (isJsonLeaf(type)) {
                    jsonObject.put(field.getName(), field.get(object).toString());
                } else if (type.isArray()) {
                    jsonObject.put(field.getName(), arrToJsonArray(field.get(object)));
                } else if (Collection.class.isAssignableFrom(type)) {
                    Collection fieldCollection = (Collection) field.get(object);
                    jsonObject.put(field.getName(), arrToJsonArray(fieldCollection.toArray()));
                } else {
                    jsonObject.put(field.getName(), objectToJsonObject(field.get(object)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private JSONArray arrToJsonArray(Object array) {
        JSONArray jsonArray = new JSONArray();
        int length = Array.getLength(array);
        for (int i = 0; i < length; i++) {
            Object arrayElement = Array.get(array, i);
            if (isJsonLeaf(arrayElement.getClass())) {
                jsonArray.add(arrayElement);
            } else if (arrayElement.getClass().isArray()) {
                jsonArray.add(arrToJsonArray(arrayElement));
            } else {
                jsonArray.add(objectToJsonObject(arrayElement));
            }
        }
        return jsonArray;
    }

    private boolean isJsonLeaf(Class<?> c) {
        return c.isPrimitive() || finiteClasses.contains(c);
    }
}
