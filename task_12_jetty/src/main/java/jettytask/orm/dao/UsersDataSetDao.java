package jettytask.orm.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import jettytask.orm.base.entity.UsersDataSet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class UsersDataSetDao {

    private Session session;

    public UsersDataSetDao(Session session) {
        this.session = session;
    }

    public void save(UsersDataSet dataSet) {
        session.save(dataSet);
    }

    public UsersDataSet read(long id) {
        return session.load(UsersDataSet.class, id);
    }

    public UsersDataSet readByName(String name) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UsersDataSet> criteria = builder.createQuery(UsersDataSet.class);
        Root<UsersDataSet> from = criteria.from(UsersDataSet.class);
        criteria.where(builder.equal(from.get("name"), name));
        Query<UsersDataSet> query = session.createQuery(criteria);
        return query.uniqueResult();
    }

    public long countUsers() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        criteria.select(builder.count(criteria.from(UsersDataSet.class)));
        Query<Long> query = session.createQuery(criteria);
        return query.getSingleResult();
    }
}