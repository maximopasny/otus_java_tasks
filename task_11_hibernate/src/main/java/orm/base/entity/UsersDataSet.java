package orm.base.entity;

import org.hibernate.annotations.Cascade;
import orm.base.DataSet;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Table(name = "users")
@Entity
public class UsersDataSet extends DataSet {

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @Basic
    @Column(name = "age", nullable = false)
    private Integer age;

    @OneToOne
    @JoinColumn(name = "addressId", referencedColumnName = "id")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private AddressDataSet address;

    @OneToMany
    @JoinColumn(name = "userId")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<PhoneDataSet> phones;

    public UsersDataSet(String name,
                        Integer age,
                        AddressDataSet address,
                        List<PhoneDataSet> phones) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.phones = phones;
    }

    public UsersDataSet(String name,
                        Integer age) {
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

    public AddressDataSet getAddress() {
        return address;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    public List<PhoneDataSet> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDataSet> phones) {
        this.phones = phones;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAge());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersDataSet that = (UsersDataSet) o;
        return Objects.equals(getName(), that.getName()) &&
                Objects.equals(getAge(), that.getAge()) &&
                Objects.equals(getAddress(), that.getAddress()) &&
                Objects.equals(getPhones(), that.getPhones());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UsersDataSet.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("age=" + age)
                .add("address=" + address)
                .add("phones=" + phones)
                .add("id=" + id)
                .toString();
    }
}
