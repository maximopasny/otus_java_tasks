package jettytask.servlet;

import jettytask.orm.base.DBService;
import jettytask.orm.base.entity.UsersDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configurable
public class AddUserServlet extends HttpServlet {
    public void setDbService(DBService dbService) {
        this.dbService = dbService;
    }

    @Autowired
    private DBService dbService;
    private static final Logger logger = LoggerFactory.getLogger(AddUserServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

    }

    public AddUserServlet() {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        Integer age = Integer.parseInt(req.getParameter("age"));
        logger.info("age is {}", age);
        String name = req.getParameter("name");
        logger.info("name is {}", name);
        resp.setContentType("text/html;charset=utf-8");
        UsersDataSet newUser = new UsersDataSet(name, age);
        dbService.saveUser(newUser);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
