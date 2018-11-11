package jettytask;

import jettytask.orm.base.DBService;
import jettytask.orm.base.DBServiceHibernateImpl;
import jettytask.servlet.AddUserServlet;
import jettytask.servlet.CountUsersServlet;
import jettytask.servlet.GetUserServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    private final static int PORT = 8090;
    private final static String PUBLIC_HTML = "public_html";

    public static void main(String[] args) throws Exception {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        DBService dbService = new DBServiceHibernateImpl();
        context.addServlet(new ServletHolder(new CountUsersServlet(dbService)), "/count");
        context.addServlet(new ServletHolder(new AddUserServlet(dbService)), "/adduser");
        context.addServlet(new ServletHolder(new GetUserServlet(dbService)), "/getuser");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();
        server.join();
    }
}
