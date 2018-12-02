package jettytask.orm.base;

import jettytask.orm.base.entity.UsersDataSet;

public interface DBService {
    void saveUser(UsersDataSet usersDataSet);
    UsersDataSet findById(Long id);
    UsersDataSet findByName(String name);
    long count();
}
