package client.gui;

import client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Okno klienta, zawiera boardPanel wyświetlający Hexy
 * TODO: panel z przyciskiem pasowania
 */
public class ClientFrame extends JFrame {

    private BoardHex[][] board = new BoardHex[25][17];
    protected JPanel boardPanel;
    protected Client client;
    boolean selected; // czy został wybrany pierwszy target move?
    int x1; int y1;
    public ClientFrame(Client client) {
        this.client = client;
        selected = false;
        createBoard();
        this.getContentPane().add(boardPanel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void createBoard() {
        boardPanel = new JPanel();
        boardPanel.setBackground(Color.black);
        boardPanel.setLayout(new GridLayout(17, 25, 2, 2));
        for (var i = 0; i < 25; i++)
        {
            for (int j = 0; j < 17; j++) {

                final int I = i;
                final int J = j;
                board[i][j] = new BoardHex();
                board[i][j].addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        if (!selected)
                        {
                            selected = true;
                            x1 = I;
                            y1 = J;
                        }
                        else
                        {
                            selected = false;
                            client.commandWriter.move(x1,y1,I,J);
                        }
                    }
                });
                boardPanel.add(board[i][j]);
            }
        }

    }
    public void updateBoard()
    {
        for (var i = 0; i < 25; i++)
        {
            for (int j = 0; j < 17; j++) {
                board[i][j].setColor(client.gameState.getHexAt(i,j).getState());
                board[i][j].repaint();
            }
            }
    }

}
