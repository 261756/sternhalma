package server;

import hex.Hex;

import java.net.Socket;
import java.util.Arrays;

/**
 * Klasa zawierająca planszę, informacje o stanie rozgrywi
 */
public class GameState {
    private Hex[][] hexes;
    GameState()
    {
        hexes = new Hex[25][17];
        initBoard(2);
    }

    /**
     * TODO: budowanie prawdziwej gwiazdy dawida, teraz jest placeholder
     * @param numberOfPlayers - liczba graczy na podstawie ktorej trzeba zbudowac różne plansze
     */
    public void initBoard(int numberOfPlayers)
    {
        for (int i = 0; i < 25; i++)
        {
            for (int j = 0; j < 17; j++)
            {
                hexes[i][j] = new Hex(Hex.State.EMPTY);
            }
        }
        hexes[0][0] = new Hex(Hex.State.PLAYER1);
        hexes[24][15] = new Hex(Hex.State.PLAYER2);
    }

    public Hex[][] getHexes() {
        return hexes;
    }

    /**
     * TODO: Sprawdza czy teraz jest tura gracza który używa danego socket
     * @param socket
     * @return
     */
    public Boolean checkTurn(Socket socket)
    {
        return true;
    }

    /**
     * TODO: Pause turę gracza socket
     * @param socket
     */
    public void passTurn(Socket socket)
    {

    }
    public void move(int a, int b, int c ,int d)
    {
        if (moveIsLegal(a,b,c,d))
        {
            Hex.State temp = hexes[a][b].getState();
            hexes[a][b].setState(hexes[c][d].getState());
            hexes[c][d].setState(temp);
        }
    }

    /**
     * TODO: Używa zasad gry aby sprawdzić czy ruch jest legalny
     * @return
     */
    public Boolean moveIsLegal(int a, int b, int c ,int d) {
        return true;
    }
}
