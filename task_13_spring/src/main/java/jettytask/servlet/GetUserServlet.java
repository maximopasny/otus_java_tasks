package jettytask.servlet;

import jettytask.orm.base.DBService;
import jettytask.orm.base.entity.UsersDataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configurable
public class GetUserServlet extends HttpServlet {
    @Autowired
    private DBService dbService;

    public void setDbService(DBService dbService) {
        this.dbService = dbService;
    }

    public GetUserServlet() {

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        resp.setContentType("text/html;charset=utf-8");
        UsersDataSet user = dbService.findByName(name);
        resp.getWriter().println(user.toString());
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
