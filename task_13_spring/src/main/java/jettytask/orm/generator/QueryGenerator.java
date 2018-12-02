package jettytask.orm.generator;

import jettytask.orm.base.DataSet;
import jettytask.orm.model.StatementArgsAndQuery;

public interface QueryGenerator {
    <T extends DataSet> StatementArgsAndQuery getSaveQuery(T entity) throws IllegalAccessException;
    <T extends DataSet> StatementArgsAndQuery getLoadQuery(Long id, Class<T> clazz);
}
