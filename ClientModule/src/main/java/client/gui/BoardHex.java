package client.gui;

import hex.Hex;

import javax.swing.*;
import java.awt.*;

/**
 * Pojedynczy Hex planszy, którego kolor zależy od listy hexów którą otrzymuje od serwera klient
 */
public class BoardHex extends JPanel{


    public BoardHex() {
        setBackground(Color.white);
        setLayout(new GridBagLayout());

    }

    public void setColor(Hex.State state) {
        if (state == Hex.State.PLAYER1)
            this.setBackground(Color.red);
        if (state == Hex.State.PLAYER2)
            this.setBackground(Color.blue);
        if (state == Hex.State.EMPTY)
            this.setBackground(Color.white);
        if (state == Hex.State.NULL)
            this.setBackground(Color.black);

    }
}
