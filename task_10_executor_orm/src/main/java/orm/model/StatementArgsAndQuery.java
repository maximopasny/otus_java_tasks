package orm.model;

import java.util.List;

public class StatementArgsAndQuery {
    private final List<StatementArg> arguments;
    private final String query;

    public StatementArgsAndQuery(List<StatementArg> arguments, String query) {
        this.arguments = arguments;
        this.query = query;
    }

    public List<StatementArg> getArguments() {
        return arguments;
    }

    public String getQuery() {
        return query;
    }
}
