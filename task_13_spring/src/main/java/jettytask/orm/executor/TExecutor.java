package jettytask.orm.executor;

import jettytask.orm.base.DataSet;

public interface TExecutor {
    <T extends DataSet> void save(T entity) throws IllegalAccessException;
    <T extends DataSet> T load(long id, Class<T> clazz);
}
