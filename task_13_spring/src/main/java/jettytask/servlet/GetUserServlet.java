package jettytask.servlet;

import jettytask.orm.base.DBService;
import jettytask.orm.base.entity.UsersDataSet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetUserServlet extends HttpServlet {
    private DBService dbService;

    public GetUserServlet(DBService dbService) {
        this.dbService = dbService;
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
