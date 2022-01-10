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
    private Boolean hovered; // czy nad polem jest myszka

    /**
     * Standardowy konstruktor BoardHex.
     */
    public BoardHex() {
        selected = false;
        hovered = false;
        color = Color.white;
        this.setOpaque(false);
    }

    /**
     * Ustawia kolor BoardHexu na podstawie Hex.State
     * @param state stan na podstawie którego ustalamy kolor
     */
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
        else if (hovered)
        {
            g.setColor(new Color(235, 51, 255, 173));
        }
        else {
            g.setColor(Color.black);
        }
        int pWidth = this.getWidth();
        g.fillOval((int)(0.05*pWidth/2),(int)(0.05*pWidth/2),(int)(0.95*pWidth),(int)(0.95*pWidth));
        g.setColor(color);
        g.fillOval((int)(0.15*pWidth/2),(int)(0.15*pWidth/2),(int)(0.85*pWidth),(int)(0.85*pWidth));
    }

    /**
     * Ustawia stan wybrania BoardHexa
     * @param set - 1 - wybrany, 0 - nie wybrany
     */
    public void setSelected(boolean set)
    {
        selected = set;
    }

    /**
     * Zwraca czy pole wybrane
     * @return true, jeśli pole wybrane
     */
    public boolean getSelected()
    {
        return selected;
    }

    /**
     * Ustawia flagę informującą, czy pole jest najechane
     * @param set true- jeśli pole najechane, false w przeciwnym wypadku
     */
    public void setHoveredOver(boolean set) {
        hovered = set;
    }
}
