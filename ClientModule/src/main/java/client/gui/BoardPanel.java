package client.gui;

import client.Client;
import hex.Hex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.channels.CancelledKeyException;

/**
 * Panel zawierający planszę
 */
public class BoardPanel extends JPanel {
    private ClientFrame clientFrame;
    private boolean isBoardCreated = false;
    final static int xAxis = 13;
    final static int yAxis = 17;
    private final Color boardColor;
    private final BoardHex[][] board = new BoardHex[xAxis][yAxis];
    boolean selected; // czy został wybrany pierwszy target move?
    int x1; int y1;

    /**
     * Standardowy konstruktor BoardPanela.
     * @param CF - Frame w którym znajduje się BoardPanel
     */
    public BoardPanel(ClientFrame CF)
    {
        boardColor =  new Color(108, 78, 40);
        selected = false;
        clientFrame = CF;
    }

    /**
     * Funkcja tworząca strukturę Panelu.
     */
    public void createBoard() {

        this.setBackground(boardColor);
        this.setLayout(new GridLayout(yAxis,1,0,0));//rzędy
        for (int j = 0; j < yAxis; j++)
        {
            JPanel p = new JPanel();
            p.setOpaque(false);
            p.setLayout(new FlowLayout(FlowLayout.CENTER,(int)(this.getHeight()*0.118/yAxis),0));
            for (int i = 0; i < xAxis; i++) {

                final int I = i;
                final int J = j;
                board[i][j] = new BoardHex();
                board[i][j].addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        if (!selected && clientFrame.client.gameState.getHexAt(I,J).getState() == clientFrame.client.getPegsColor())
                        {
                            selected = true;
                            board[I][J].setSelected(true);
                            board[I][J].repaint();
                            x1 = I;
                            y1 = J;
                        }
                        else if (selected)
                        {
                            selected = false;
                            board[x1][y1].setSelected(false);
                            board[x1][y1].repaint();
                            if (clientFrame.client.gameState.getHexAt(I,J).getState() == Hex.State.EMPTY) {
                                clientFrame.client.commandWriter.move(x1, y1, I, J);
                            }
                        }
                    }
                    public void mouseEntered(MouseEvent e)
                    {
                            board[I][J].setHoveredOver(true);
                            board[I][J].repaint();

                    }
                    public void mouseExited(MouseEvent e)
                    {
                        board[I][J].setHoveredOver(false);
                        board[I][J].repaint();
                    }


                });
                if (clientFrame.client.gameState.getHexAt(i,j).getState() != Hex.State.NULL) {
                    p.add(board[i][j]);
                }
                if (i == xAxis-1) {
                    this.add(p);
                }
            }
        }
        isBoardCreated = true;
    }

    /**
     * Funkcja updatująca wygląd BoardPanelu.
     */
    public void updateBoard()
    {
        if (isBoardCreated) {
            for (var i = 0; i < xAxis; i++)
            {
                for (int j = 0; j < yAxis; j++) {
                    board[i][j].setColor(clientFrame.client.gameState.getHexAt(i,j).getState());
                    board[i][j].repaint();
                }
            }
        } else {
            createBoard();
            clientFrame.sidePanel.displaySidePanel();
            this.revalidate();
            this.repaint();
        }

    }
}
