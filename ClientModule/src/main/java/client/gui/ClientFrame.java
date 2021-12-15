package client.gui;

import client.Client;

import javax.swing.*;

/**
 * Okno klienta
 * TODO:
 */
public class ClientFrame extends JFrame {
    protected JPanel board;
    protected Client client;
    public ClientFrame(Client client) {
        this.client = client;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void createBoard() {
        board = new JPanel();
    }

}
