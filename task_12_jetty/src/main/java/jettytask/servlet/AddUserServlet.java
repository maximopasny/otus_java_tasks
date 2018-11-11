package jettytask.servlet;

import jettytask.orm.base.DBService;
import jettytask.orm.base.entity.UsersDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddUserServlet extends HttpServlet {
    private DBService dbService;
    private static final Logger logger = LoggerFactory.getLogger(AddUserServlet.class);

    public AddUserServlet(DBService dbService) {
        this.dbService = dbService;
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
