package server;

import hex.Hex;
import server.boardTools.AbstractRegionFactory;

/**
 * Abstrakcyjny sprawdzacz ruchów
 */
public abstract class AbstractMoveValidator {
    /**
     * Metoda sprawdzająca, czy ruch (a,b) -> (c,d) jest zgodny z zasadami
     * @param a x początkowe
     * @param b y początkowe
     * @param c x końcowe
     * @param d y końcowe
     * @return true -> gdy ruch poprawny, false -> w przeciwnym wypadku
     */
    public boolean moveIsLegal(int a, int b, int c, int d) {
        return true;
    }

    /**
     * Metoda zaczynająca nową turę dla gracza o danym kolorze
     * @param color kolor gracza
     */
    public abstract void newTurn(Hex.State color);

    /**
     * Ustawia powiązany stan gry
     * @param game stan gry
     */
    public abstract void setGameState(GameState game);

    /**
     * Ustawia powiązane definicje regionów
     * @param factory definicje regionów
     */
    public abstract void setRegionFactory(AbstractRegionFactory factory);
}
