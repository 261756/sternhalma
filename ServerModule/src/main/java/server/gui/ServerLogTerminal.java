package server.gui;

/**
 * Klasa logująca grę do terminala
 */
public class ServerLogTerminal implements ServerLog{

    @Override
    public void log(String msg) {
        System.out.println(msg);
    }
}
