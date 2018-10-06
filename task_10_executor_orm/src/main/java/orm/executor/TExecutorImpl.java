package orm.executor;

import orm.base.DataSet;
import orm.generator.QueryGenerator;
import orm.h2source.Database;
import orm.model.StatementArg;
import orm.model.StatementArgsAndQuery;
import orm.util.ORMUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class TExecutorImpl implements TExecutor {
    private final Database database;
    private final QueryGenerator queryGenerator;

    public TExecutorImpl(Database database, QueryGenerator queryGenerator) {
        this.database = database;
        this.queryGenerator = queryGenerator;
    }

    @Override
    public <T extends DataSet> void save(T entity) throws IllegalAccessException {
        StatementArgsAndQuery queryWithArgs = queryGenerator.getSaveQuery(entity);
        try (PreparedStatement preparedStatement = database.getConnection().prepareStatement(queryWithArgs.getQuery(), Statement.RETURN_GENERATED_KEYS)) {
            List<StatementArg> args = queryWithArgs.getArguments();
            for (StatementArg arg : args) {
                arg.setArg(preparedStatement);
            }
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();

            if (rs.next()) {
                long id = rs.getLong(1);
                ORMUtils.setId(entity, id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        StatementArgsAndQuery queryWithArgs = queryGenerator.getLoadQuery(id, clazz);
        try (PreparedStatement preparedStatement = database.getConnection().prepareStatement(queryWithArgs.getQuery())) {
            List<StatementArg> args = queryWithArgs.getArguments();
            for (StatementArg arg : args) {
                arg.setArg(preparedStatement);
            }

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return ORMUtils.fillQueryResult(clazz, rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
