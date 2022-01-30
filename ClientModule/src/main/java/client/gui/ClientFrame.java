package client.gui;

import client.Client;

import javax.swing.*;
import java.awt.*;


/**
 * Okno klienta, zawiera boardPanel wyświetlający Hexy
 */
public class ClientFrame extends JFrame {
    /**
     * Panel z planszą
     */
    protected BoardPanel boardPanel;
    /**
     * Panel boczny
     */
    protected BasicSidePanel sidePanel;
    /**
     * Klient
     */
    protected Client client;

    /**
     * Konstruktor
     * @param client klient
     */
    public ClientFrame(Client client) {

        this.client = client;
        boardPanel = new BoardPanel(this);
        sidePanel = new SidePanel(this);
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(boardPanel,BorderLayout.CENTER);
        this.getContentPane().add(sidePanel,BorderLayout.EAST);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Odświeża planszę
     */
    public void updateBoard()
    {
        boardPanel.updateBoard();
    }

    /**
     * Wyświetla powiadomienie o turze
     * @param message powiadomienie
     */
    public void notify(String message) {
        sidePanel.setTurnInfo(message);
    }

    /**
     * Wyświetla powiadomienie o zwycięzcy
     * @param message powiadomienie
     */
    public void updateWinners(String message) {
        sidePanel.setWinnersInfo(message);
    }

    /**
     * Zwraca wcześniejsze powiadomienie o zwycięzcy
     * @return wcześniejsze powiadomienie
     */
    public String getWinnerMsg()
    {
        return sidePanel.getWinnersInfo();
    }

    /**
     * Wyświetla dialog do pobrania adresu serwera. Zwraca adres i port serwera
     * @param message wiadomość do wyświetlenia na dialogu
     * @return adres i port serwera podany przez użytkownika
     * @throws Exception gdy dialog zostanie zamknięty przez użytkownika
     */
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
    public void changeToReplayMode() {
        this.remove(sidePanel);
        this.sidePanel = new ReplaySidePanel(this);
        this.getContentPane().add(sidePanel,BorderLayout.EAST);
        this.sidePanel.displaySidePanel();
        this.validate();
        this.repaint();
    }
    public int showReplayOptions(String[] gameNumbers) {
        ReplaySetupPanel panel = new ReplaySetupPanel(gameNumbers);
        int input = JOptionPane.showConfirmDialog(this,panel,"Tryb powtórki",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE);
        if (input == JOptionPane.YES_OPTION) {
            return Integer.parseInt(panel.getSelectedGame());
        } else {
            return -1;
        }
    }
}
