package jettytask.servlet;

import jettytask.orm.base.DBService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CountUsersServlet extends HttpServlet {
    private DBService dbService;

    public CountUsersServlet(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=utf-8");
        long count = dbService.count();
        resp.getWriter().println(Long.toString(count));
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
