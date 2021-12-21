package hex;

import java.io.Serializable;

/**
 * Klasa pojedynczego pola, które przyjmuje Stany.
 */
public class Hex implements Serializable {
    public enum State{
        NULL, EMPTY, RED, BLUE, WHITE, YELLOW, GREEN, BLACK;
    }
    public State state;
    public Hex(State s)
    {
        state = s;
    }
    public State getState()
    {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
