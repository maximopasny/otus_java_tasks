package orm.generator;

import orm.base.DataSet;
import orm.model.StatementArgsAndQuery;

public interface QueryGenerator {
    <T extends DataSet> StatementArgsAndQuery getSaveQuery(T entity) throws IllegalAccessException;
    <T extends DataSet> StatementArgsAndQuery getLoadQuery(Long id, Class<T> clazz);
}
