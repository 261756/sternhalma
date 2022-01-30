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
    public static int getNumberOfPlayers(int gameId) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        ConnectionJDBC connectionJDBC =
                (ConnectionJDBC) context.getBean("connectionJDBC");
        return connectionJDBC.getNumberOfPlayers(gameId);
    }
    public static int[] getMove(int gameId, int moveNumber) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        ConnectionJDBC connectionJDBC =
                (ConnectionJDBC) context.getBean("connectionJDBC");
        SimpleMove move = connectionJDBC.getMove(gameId, moveNumber);
        int[] result = new int[4];
        result[0] = move.getX1();
        result[1] = move.getY1();
        result[2] = move.getX2();
        result[3] = move.getY2();
        return result;
    }
    public static int getNumberOfMoves(int gameId) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        ConnectionJDBC connectionJDBC =
                (ConnectionJDBC) context.getBean("connectionJDBC");
        return connectionJDBC.getNumberOfMoves(gameId);
    }
}
