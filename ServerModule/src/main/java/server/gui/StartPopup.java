package server.gui;

import server.NoGUIInitializer;
import server.Server;
import javax.swing.*;
import java.awt.*;

/**
 * Popup startowy, który konfiguruje serwer
 */
public class StartPopup extends JFrame {

    private final Server server;
    private final PortField portField = new PortField(this);
    private final ComboNumberOfPlayers comboNumberOfPlayers = new ComboNumberOfPlayers(this);

    /**
     * Konstruktor okienka
     * @param server serwer do konfiguracji
     */
    public StartPopup(Server server)
    {
        this.setTitle("Wybierz parametry");
        this.getContentPane().setLayout(new GridLayout(3,1));
        this.server = server;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400,200);
        this.setLocationRelativeTo(null);
        JPanel contain1 = new JPanel(new GridLayout(1,2));
        contain1.add(new JLabel("Numer portu: "));
        contain1.add(portField);
        JPanel contain2 = new JPanel(new GridLayout(1,2));
        contain2.add(new JLabel("Liczba graczy: "));
        contain2.add(comboNumberOfPlayers);
        this.getContentPane().add(contain1);
        this.getContentPane().add(contain2);
        //this.getContentPane().add(contain);
        this.getContentPane().add(new AcceptButton(this));
        this.setVisible(true);
    }

    /**
     * Wysysła do serwera port i liczbe graczy jednocześnie startując go
     */
    public boolean sendInput() {
        try {
            int port = Integer.parseInt(portField.getText());
            if (port > 65535 || port < 0)
            {
                throw new NumberFormatException();
            }
            int numberOfPlayers = (int) comboNumberOfPlayers.getSelectedItem();
            Thread serverThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        server.startServer(port, numberOfPlayers);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            serverThread.start();
            Thread.sleep(10);
            if (serverThread.getState() == Thread.State.TERMINATED)
            {
                //System.out.println("terminated");
                return false;
            }


        }
        catch (NumberFormatException e)
        {
            server.log("Podano nieprawidłowe dane");
        }
        catch (Exception e) {
            System.exit(0);
        }
        return true;
    }

    /**
     * Startuje serwer, bez użycia okienek
     * @param port port, na którym działa serwer
     * @param numberOfPlayers liczba graczy
     */
    public void sendInputNoGUI(String port, String numberOfPlayers) {
            this.setVisible(false);
            Thread serverThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        NoGUIInitializer.startServerNoGUI(server,port,numberOfPlayers);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            serverThread.start();
    }

}
