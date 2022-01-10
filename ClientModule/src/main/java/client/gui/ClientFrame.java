package client.gui;

import client.Client;
import hex.Hex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 * Okno klienta, zawiera boardPanel wyświetlający Hexy
 */
public class ClientFrame extends JFrame {

    protected BoardPanel boardPanel;
    protected SidePanel sidePanel;
    protected Client client;
    public ClientFrame(Client client) {

        this.client = client;
        boardPanel = new BoardPanel(this);
        sidePanel = new SidePanel(this);
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(boardPanel,BorderLayout.CENTER);
        this.getContentPane().add(sidePanel,BorderLayout.EAST);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void updateBoard()
    {
        boardPanel.updateBoard();
    }
    public void notify(String message) {
        sidePanel.setTurnInfo(message);
    }
    public void updateWinners(String message) {
        sidePanel.setWinnersInfo(message);
    }
    public String getWinnerMsg()
    {
        return sidePanel.getWinnersInfo();
    }
    public String[] showSetupOptions(String message) throws Exception {
        SetupPanel panel = new SetupPanel();
        panel.setInfoLabel(message);
        int input = JOptionPane.showConfirmDialog(this,panel,"Skonfiguruj połączenie",JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE);
        if (input == JOptionPane.OK_OPTION) {
            return new String[]{panel.getAddressText(), panel.getPortText()};
        } else {
            throw new Exception();
        }
    }

}
