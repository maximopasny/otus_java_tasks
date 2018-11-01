package orm.generator;

import orm.base.DataSet;
import orm.model.StatementArg;
import orm.model.StatementArgsAndQuery;
import orm.model.TypesSupported;
import orm.util.ORMUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QueryGeneratorImpl implements QueryGenerator {
    public static final String INSERT_INTO = "INSERT INTO";
    public static final String SELECT = "SELECT * FROM";
    public static final String WHERE = "WHERE";
    public static final String VALUES = "VALUES";

    @Override
    public <T extends DataSet> StatementArgsAndQuery getSaveQuery(T entity) throws IllegalAccessException {
        StringBuilder queryHolder = new StringBuilder();
        List<Field> fields = ORMUtils.getAllFieldsSupportedWithoutId(entity.getClass());
        String columns = fields.stream()
                .map(ORMUtils::getColumn)
                .collect(Collectors.joining(","));

        String columnsInBrackets = "(" + columns + ")";
        List<StatementArg> args = new ArrayList<>();
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            field.setAccessible(true);
            Object value = field.get(entity);
            int type = TypesSupported.getSqlType(field.getType());
            args.add(new StatementArg(value, i + 1, type));
        }

        String qMarksRepeated = IntStream.range(0, fields.size()).mapToObj(i -> "?").collect(Collectors.joining(","));

        queryHolder.append(INSERT_INTO)
                .append(" ")
                .append(ORMUtils.getTable(entity.getClass()))
                .append(" ")
                .append(columnsInBrackets)
                .append(" ")
                .append(VALUES)
                .append(" ")
                .append("(" + qMarksRepeated + ")")
                .append(";");

        StatementArgsAndQuery stmt = new StatementArgsAndQuery(args, queryHolder.toString());
        return stmt;
    }

    @Override
    public <T extends DataSet> StatementArgsAndQuery getLoadQuery(Long id, Class<T> clazz) {
        StringBuilder queryHolder = new StringBuilder();
        queryHolder.append(SELECT)
                .append(" ")
                .append(ORMUtils.getTable(clazz))
                .append(" ")
                .append(WHERE)
                .append(" ")
                .append(ORMUtils.getIdFieldName(clazz))
                .append("=")
                .append("?")
                .append(";");

        List<StatementArg> args = List.of(new StatementArg(id, 1, TypesSupported.getSqlType(Long.class)));
        StatementArgsAndQuery stmt = new StatementArgsAndQuery(args, queryHolder.toString());
        return stmt;
    }
}
