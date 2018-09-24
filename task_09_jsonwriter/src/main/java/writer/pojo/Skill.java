package writer.pojo;

import java.util.Objects;

public class Skill {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill1 = (Skill) o;
        return Objects.equals(getSkill(), skill1.getSkill());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSkill());
    }

    private final String skill;

    private Skill() {
        skill = null;
    }

    public Skill(String skill) {
        this.skill = skill;
    }

    public String getSkill() {
        return skill;
    }
}
