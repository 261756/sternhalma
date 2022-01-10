package hex;

import java.io.Serializable;

/**
 * Klasa pojedynczego pola, które przyjmuje Stany.
 */
public class Hex implements Serializable {
    /**
     * Możliwe stany Hex
     */
    public enum State{
        NULL, EMPTY, RED, BLUE, WHITE, YELLOW, GREEN, BLACK
    }

    /**
     * Stan Hexa
     */
    public State state;

    /**
     * Konstruktor, ustawia stan hexa
     * @param s stan hexa do ustawienia
     */
    public Hex(State s)
    {
        state = s;
    }

    /**
     * Zwraca stan hexa
     * @return stan hexa
     */
    public State getState()
    {
        return state;
    }

    /**
     * Zmienia stan hexa
     * @param state stan hexa do ustawienia
     */
    public void setState(State state) {
        this.state = state;
    }
}
