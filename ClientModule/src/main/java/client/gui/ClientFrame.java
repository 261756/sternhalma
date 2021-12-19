package client.gui;

import client.Client;
import hex.Hex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 * Okno klienta, zawiera boardPanel wyświetlający Hexy
 * TODO: panel z przyciskiem pasowania
 */
public class ClientFrame extends JFrame {

    protected BoardPanel boardPanel;
    protected Client client;
    public ClientFrame(Client client) {

        this.client = client;
        boardPanel = new BoardPanel(this);
        this.getContentPane().add(boardPanel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void updateBoard()
    {
        boardPanel.updateBoard();
    }



}
