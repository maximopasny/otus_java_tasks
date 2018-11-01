package orm.executor;

import orm.base.DataSet;

public interface TExecutor {
    <T extends DataSet> void save(T entity) throws IllegalAccessException;
    <T extends DataSet> T load(long id, Class<T> clazz);
}
