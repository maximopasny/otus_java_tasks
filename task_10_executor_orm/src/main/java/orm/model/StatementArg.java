package orm.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StatementArg {
    private final Object arg;
    private final int pos;
    private final int sqlType;

    public StatementArg(Object arg, int pos, int sqlType) {
        this.arg = arg;
        this.pos = pos;
        this.sqlType = sqlType;
    }

    public void setArg(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setObject(pos, arg, sqlType);
    }
}
