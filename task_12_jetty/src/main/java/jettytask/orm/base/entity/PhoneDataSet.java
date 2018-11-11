package jettytask.orm.base.entity;

import jettytask.orm.base.DataSet;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;
import java.util.StringJoiner;

@Table(name = "phones")
@Entity
public class PhoneDataSet extends DataSet {
    @Column(name = "number", nullable = false)
    @Basic
    private String number;

    @Column(name = "userId")
    @Basic
    private Long userId;

    public PhoneDataSet(String number) {
        this.number = number;
    }

    public PhoneDataSet() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneDataSet that = (PhoneDataSet) o;
        return Objects.equals(getNumber(), that.getNumber()) &&
                Objects.equals(getUserId(), that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber(), getUserId());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PhoneDataSet.class.getSimpleName() + "[", "]")
                .add("number='" + number + "'")
                .add("userId=" + userId)
                .add("id=" + id)
                .toString();
    }
}
