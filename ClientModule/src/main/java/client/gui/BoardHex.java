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
        if (state == Hex.State.RED)
            this.color = (Color.red);
        if (state == Hex.State.BLUE)
            this.color = (Color.blue);
        if (state == Hex.State.WHITE)
            this.color = (Color.white);
        if (state == Hex.State.BLACK)
            this.color = (Color.black);
        if (state == Hex.State.GREEN)
            this.color = (Color.green);
        if (state == Hex.State.YELLOW)
            this.color = (Color.yellow);
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
            g.setColor(new Color(255, 217, 51, 173));
        }
        else {
            g.setColor(Color.black);
        }
        int pWidth = this.getWidth();
        //g.fillOval(0,0,43,43);
        g.fillOval((int)(0.05*pWidth/2),(int)(0.05*pWidth/2),(int)(0.95*pWidth),(int)(0.95*pWidth));
        g.setColor(color);
        g.fillOval((int)(0.15*pWidth/2),(int)(0.15*pWidth/2),(int)(0.85*pWidth),(int)(0.85*pWidth));
        //g.fillOval(0,0,40,40);
    }
    public void setSelected(boolean set)
    {
        selected = set;
    }

}
