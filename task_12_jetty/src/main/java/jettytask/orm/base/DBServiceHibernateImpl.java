package jettytask.orm.base;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import jettytask.orm.base.entity.AddressDataSet;
import jettytask.orm.base.entity.PhoneDataSet;
import jettytask.orm.base.entity.UsersDataSet;
import jettytask.orm.dao.UsersDataSetDao;

import java.util.function.Consumer;
import java.util.function.Function;

public class DBServiceHibernateImpl implements DBService {
    private final SessionFactory sessionFactory;

    public DBServiceHibernateImpl() {
        Configuration c = new Configuration();
        c.addAnnotatedClass(DataSet.class);
        c.addAnnotatedClass(UsersDataSet.class);
        c.addAnnotatedClass(PhoneDataSet.class);
        c.addAnnotatedClass(AddressDataSet.class);
        c.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        c.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        c.setProperty("hibernate.connection.url", "jdbc:h2:file:./myorm");
        c.setProperty("hibernate.connection.username", "sa");
        c.setProperty("hibernate.connection.password", "");
        c.setProperty("hibernate.show_sql", "true");
        c.setProperty("hibernate.hbm2ddl.auto", "create");
        c.setProperty("hibernate.connection.useSSL", "false");
        c.setProperty("hibernate.enable_lazy_load_no_trans", "true");
        sessionFactory = createSessionFactory(c);
    }

    @Override
    public UsersDataSet findById(Long id) {
        return runInSession(session -> {
            UsersDataSetDao dao = new UsersDataSetDao(session);
            return dao.read(id);
        });
    }

    @Override
    public UsersDataSet findByName(String name) {
        return runInSession(session -> {
            UsersDataSetDao dao = new UsersDataSetDao(session);
            return dao.readByName(name);
        });
    }

    @Override
    public long count() {
        return runInSession(session -> {
            UsersDataSetDao dao = new UsersDataSetDao(session);
            return dao.countUsers();
        });
    }

    @Override
    public void saveUser(UsersDataSet usersDataSet) {
        runInSession(session -> {
            UsersDataSetDao dao = new UsersDataSetDao(session);
            dao.save(usersDataSet);
        });
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }

    private void runInSession(Consumer<Session> consumer) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            consumer.accept(session);
            transaction.commit();
        }
    }
}
