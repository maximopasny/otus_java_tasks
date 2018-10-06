package orm.base;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Objects;
import java.util.StringJoiner;

@Table(name = "users")
public class UsersDataSet extends DataSet {
    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAge());
    }

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    public UsersDataSet(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public UsersDataSet() {

    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UsersDataSet.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("name='" + name + "'")
                .add("age=" + age)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersDataSet that = (UsersDataSet) o;
        return Objects.equals(getName(), that.getName()) &&
               Objects.equals(getAge(), that.getAge())
               && Objects.equals(getId(), that.getId());
    }
}
