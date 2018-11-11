package jettytask.orm.base.entity;

import jettytask.orm.base.DataSet;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.StringJoiner;

@Table(name = "addresses")
@Entity
public class AddressDataSet extends DataSet {
    public AddressDataSet() {
    }

    @Column(name = "street", nullable = false)
    @Basic
    private String street;

    @Column(name = "userId")
    @Basic
    private Long userId;

    public AddressDataSet(String street) {
        this.street = street;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AddressDataSet.class.getSimpleName() + "[", "]")
                .add("street='" + street + "'")
                .add("userId=" + userId)
                .add("id=" + id)
                .toString();
    }
}
