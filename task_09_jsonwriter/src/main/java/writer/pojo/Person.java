package writer.pojo;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Person {
    private int age;
    private boolean gender;
    private double height;
    private Integer nullableWrapperField;
    private Skill nullableNotWrapperSkill;
    private List<Skill> skillsList;
    private Skill[] skillsArray;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public List<Skill> getSkillsList() {
        return skillsList;
    }

    public void setSkillsList(List<Skill> skillsList) {
        this.skillsList = skillsList;
    }

    public Skill[] getSkillsArray() {
        return skillsArray;
    }

    public void setSkillsArray(Skill[] skillsArray) {
        this.skillsArray = skillsArray;
    }

    public Integer getNullableWrapperField() {
        return nullableWrapperField;
    }

    public void setNullableWrapperField(Integer nullableWrapperField) {
        this.nullableWrapperField = nullableWrapperField;
    }

    public Skill getNullableNotWrapperSkill() {
        return nullableNotWrapperSkill;
    }

    public void setNullableNotWrapperSkill(Skill nullableNotWrapperSkill) {
        this.nullableNotWrapperSkill = nullableNotWrapperSkill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return getAge() == person.getAge() &&
                isGender() == person.isGender() &&
                Double.compare(person.getHeight(), getHeight()) == 0 &&
                Objects.equals(getNullableWrapperField(), person.getNullableWrapperField()) &&
                Objects.equals(getNullableNotWrapperSkill(), person.getNullableNotWrapperSkill()) &&
                Arrays.equals(getSkillsList().toArray(), person.getSkillsList().toArray()) &&
                Arrays.equals(getSkillsArray(), person.getSkillsArray());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getAge(), isGender(), getHeight(), getNullableWrapperField(), getNullableNotWrapperSkill(), getSkillsList());
        result = 31 * result + Arrays.hashCode(getSkillsArray());
        return result;
    }
}
