package orm.base.entity;

import orm.base.DataSet;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
}
