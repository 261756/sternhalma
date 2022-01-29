package serverTests;

import org.junit.Test;
import server.sql.QuerySQL;

import java.util.Arrays;

public class SQLtest {
    @Test
    public void test1() {
        String s = "gameEnded" + QuerySQL.getGameList();
        String list = s.substring(9);
        System.out.println(list);

        Integer[] result = Arrays.stream(s.substring(9).split(" ")).mapToInt(Integer::parseInt).boxed().toArray(Integer[]::new);
        for (Integer i : result) {
            System.out.println(i.intValue());
        }
    }
}
