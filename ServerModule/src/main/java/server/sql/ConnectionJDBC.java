package server.sql;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import org.apache.commons.codec.digest.DigestUtils;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

public class ConnectionJDBC {
    private javax.sql.DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    public boolean createMove(int gameId, int moveNumber, int x1, int y1, int x2, int y2, String color_name) {
        String SQL = "insert into warcaby.move (id, game_id, number, x1,y1,x2,y2, color) values (NULL, ?, ?, ?, ?, ?, ?, ?)";
        try {
            return jdbcTemplateObject.update(SQL, gameId, moveNumber,x1,y1,x2,y2,color_name) != 0;
        }
        catch (DuplicateKeyException e)
        {
            return false;
        }
    }

    /**
     *
     * @param numberOfPlayers liczba graczy
     * @return numer gry w tabeli SQL
     */
    public int createGame(int numberOfPlayers) {
        String SQL = "insert into warcaby.game (id, numberOfPlayers, status) values (NULL, ?,? )";
        try {
            jdbcTemplateObject.update(SQL, numberOfPlayers,"ongoing");
        }
        catch (DuplicateKeyException e)
        {
            return -1;
        }
        int number = -1;
        String SQL2 = "SELECT max(id) FROM warcaby.game";
        Map<String, Object> map = jdbcTemplateObject.queryForMap(SQL2);
        number = (int) map.get("max(id)");
        return number;
    }

    public String getGameList() {
        String SQL = "select id from warcaby.game";
        List<String> list = jdbcTemplateObject.queryForList(SQL,String.class);
        return String.join(" ", list);
    }
}
