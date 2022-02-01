package server.sql;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class UpdateSQL {
    public static boolean addMove(int gameId, int moveNumber, int x1, int y1, int x2, int y2, String color_name)
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        ConnectionJDBC connectionJDBC =
                (ConnectionJDBC) context.getBean("connectionJDBC");
        return connectionJDBC.createMove(gameId, moveNumber, x1, y1, x2, y2, color_name);
    }
    public static int addGame(int numberOfPlayers)
    {
        System.out.println("Dodano grę");
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        ConnectionJDBC connectionJDBC =
                (ConnectionJDBC) context.getBean("connectionJDBC");
        return connectionJDBC.createGame(numberOfPlayers);
    }
    public static void setEnded(int gameId)
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        ConnectionJDBC connectionJDBC =
                (ConnectionJDBC) context.getBean("connectionJDBC");
        connectionJDBC.setEnded(gameId);
    }
}
