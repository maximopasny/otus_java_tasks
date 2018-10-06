package orm.base;

import javax.persistence.Column;
import javax.persistence.Id;

public class DataSet {
    @Id
    @Column(name = "id")
    protected Long id;

    public DataSet() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
