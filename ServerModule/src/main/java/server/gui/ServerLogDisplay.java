package server.gui;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

public class ServerLogDisplay extends JFrame {
    JTextArea textArea;
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
    public synchronized void log(String msg) {
        textArea.append("\n" + msg);
    }

}
