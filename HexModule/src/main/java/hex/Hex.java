package hex;

import java.io.Serializable;

public class Hex implements Serializable {
    public enum State{
        NULL,EMPTY,PLAYER1,PLAYER2
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
