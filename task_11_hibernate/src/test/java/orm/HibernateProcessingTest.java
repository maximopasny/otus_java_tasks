package orm;

import org.junit.Before;
import org.junit.Test;
import orm.base.DBService;
import orm.base.DBServiceHibernateImpl;
import orm.base.entity.AddressDataSet;
import orm.base.entity.PhoneDataSet;
import orm.base.entity.UsersDataSet;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class HibernateProcessingTest {
    private DBService dbService;

    @Before
    public void init() {
        dbService = new DBServiceHibernateImpl();
    }

    @Test
    public void saveEntitiesAndLoadThem() throws IllegalAccessException {
        AddressDataSet addressDataSet = new AddressDataSet("Pushkina");
        PhoneDataSet phoneDataSet = new PhoneDataSet("88005553535");
        UsersDataSet usersDataSet = new UsersDataSet("Maxim", 24, addressDataSet, List.of(phoneDataSet));

        dbService.saveUser(usersDataSet);
        dbService.findById(usersDataSet.getId());
        UsersDataSet user = dbService.findByName("Maxim");
        assertEquals(user.getName(), "Maxim");
        assertEquals(user.getPhones().size(), 1);
        assertEquals(user.getAddress().getStreet(), "Pushkina");
        assertEquals(user.getPhones().get(0).getNumber(), "88005553535");
    }
}
