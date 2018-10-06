package orm;

import org.junit.Before;
import org.junit.Test;
import orm.base.UsersDataSet;
import orm.executor.TExecutor;
import orm.executor.TExecutorImpl;
import orm.generator.QueryGenerator;
import orm.generator.QueryGeneratorImpl;
import orm.h2source.Database;

import static org.junit.Assert.assertEquals;

public class OrmProcessingTest {
    private TExecutor tExecutor;
    private Database database;
    private QueryGenerator queryGenerator;

    @Before
    public void prepare() {
        database = Database.getInstance();
        queryGenerator = new QueryGeneratorImpl();
        tExecutor = new TExecutorImpl(database, queryGenerator);
    }

    @Test
    public void saveEntitiesAndLoadThem() throws IllegalAccessException {
        UsersDataSet usersDataSet = new UsersDataSet("Maxim", 24);
        tExecutor.save(usersDataSet);
        UsersDataSet found = tExecutor.load(usersDataSet.getId(), UsersDataSet.class);
        assertEquals(usersDataSet, found);
    }
}
