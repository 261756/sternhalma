package server.gui;

import javax.swing.*;

/**
 * Okienko z logami serwera.
 */
public class ServerLogDisplay extends JFrame implements ServerLog {
    private final JTextArea textArea;

    /**
     * Konstruktor, okienko jest widoczne od razu po stworzeniu
     */
    public ServerLogDisplay()
    {
        this.setTitle("Logi serwera");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600,600);
        this.setLocationRelativeTo(null);
        textArea = new JTextArea("Otworzono logi");
        JScrollPane scroll = new JScrollPane (textArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scroll);

        this.setVisible(true);

    }

    /**
     * Wysyła logi na okienko
     * @param msg log
     */
    @Override
    public synchronized void log(String msg) {
        textArea.append("\n" + msg);
    }

}
