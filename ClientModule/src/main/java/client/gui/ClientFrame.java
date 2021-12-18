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

    private boolean isBoardCreated = false;
    final static int xAxis = 13;
    final static int yAxis = 17;
    //private BoardHex[][] board = new BoardHex[25][17];
    private BoardHex[][] board = new BoardHex[xAxis][yAxis];
    protected JPanel boardPanel;
    protected Client client;
    boolean selected; // czy został wybrany pierwszy target move?
    int x1; int y1;
    public ClientFrame(Client client) {
        this.client = client;
        selected = false;
        //createBoard();
        boardPanel = new JPanel();
        this.getContentPane().add(boardPanel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void createBoard() {
        //boardPanel = new JPanel();
        boardPanel.setBackground(Color.black);
        boardPanel.setLayout(new GridLayout(yAxis,1,0,0));//rzędy
        for (int j = 0; j < yAxis; j++)
        {
            JPanel p = new JPanel();
            //p.setBorder(BorderFactory.createLineBorder(Color.black));
            p.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
            p.add(Box.createHorizontalGlue());//lewa wolna przestrzeń
            for (int i = 0; i < xAxis; i++) {

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
                //TODO: Plansza powinna wyświetlać pola pola puste i z pionkami
                if (client.gameState.getHexAt(i,j).getState()== Hex.State.PLAYER1) {
                    p.add(board[i][j]);
                }
                if (i == xAxis-1) {
                    p.add(Box.createHorizontalGlue());//prawa wolna przestrzeń
                    boardPanel.add(p);
                }
            }
        }
        isBoardCreated = true;
    }
    public void updateBoard()
    {
        if (isBoardCreated) {
            for (var i = 0; i < xAxis; i++)
            {
                for (int j = 0; j < yAxis; j++) {
                    board[i][j].setColor(client.gameState.getHexAt(i,j).getState());
                    board[i][j].repaint();
                }
            }
        } else {
            createBoard();
            boardPanel.revalidate();
            boardPanel.repaint();
        }

    }

}
