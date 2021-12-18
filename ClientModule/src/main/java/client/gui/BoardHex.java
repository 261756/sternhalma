package client.gui;

import hex.Hex;

import javax.swing.*;
import java.awt.*;

/**
 * Pojedynczy Hex planszy, którego kolor zależy od listy hexów którą otrzymuje od serwera klient
 */
public class BoardHex extends JPanel{

    private Color color;
    private Boolean selected; // czy pole zostalo wybrane
    public BoardHex() {
        selected = false;
        color = Color.white;
        //setBackground(Color.white);
        //setBorder(BorderFactory.createLineBorder(Color.black));
        //setLayout(new GridBagLayout());

    }

    public void setColor(Hex.State state) {
        if (state == Hex.State.PLAYER1)
            this.color = (Color.red);
        if (state == Hex.State.PLAYER2)
            this.color = (Color.blue);
        if (state == Hex.State.EMPTY)
            this.color = (new Color(216,181,140));
        if (state == Hex.State.NULL)
            this.color = (Color.black);
        //setBackground(color);

    }

    @Override
    public Dimension getPreferredSize() {
        int height = this.getParent().getHeight();
        return new Dimension(height,height);
    }

    @Override
    public void paint(Graphics g)
    {

        if (selected) {
            g.setColor(Color.YELLOW);
        }
        else {
            g.setColor(Color.black);
        }
        g.fillOval(0,0,43,43);
        g.setColor(color);
        g.fillOval(0,0,40,40);
    }
    public void setSelected(boolean set)
    {
        selected = set;
    }

}
