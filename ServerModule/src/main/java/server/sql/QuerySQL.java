package server.sql;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class QuerySQL {
    public static String getGameList() {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        ConnectionJDBC connectionJDBC =
                (ConnectionJDBC) context.getBean("connectionJDBC");
        return connectionJDBC.getGameList();
    }
}
