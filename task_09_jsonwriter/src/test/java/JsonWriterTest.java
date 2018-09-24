import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import writer.JsonWriter;
import writer.JsonWriterImpl;
import writer.pojo.Person;
import writer.pojo.Skill;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;

public class JsonWriterTest {
    @Test
    public void When_PersonSerialized_Then_GsonCanDeserializeIt() {
        JsonWriter jsonWriter = new JsonWriterImpl();
        Person personInitial = new Person();
        personInitial.setAge(24);
        personInitial.setGender(false);
        personInitial.setHeight(123d);
        personInitial.setNullableNotWrapperSkill(null);
        personInitial.setNullableWrapperField(null);
        personInitial.setSkillsArray(new Skill[]{new Skill("qwe"), new Skill("asd")});
        personInitial.setSkillsList(Arrays.asList(new Skill[]{new Skill("qwe"), new Skill("asd")}));

        String json = jsonWriter.writeToJson(personInitial);
        System.out.println(json);
        Gson gson = new GsonBuilder().create();
        Person personParsed = gson.fromJson(json, Person.class);
        assertEquals(personParsed, personInitial);
    }
}
