package server.gui;

/**
 * Klasa logująca grę do terminala
 */
public class ServerLogTerminal implements ServerLog{
    /**
     * Wysyła logi do terminala
     * @param msg log
     */
    @Override
    public void log(String msg) {
        System.out.println(msg);
    }
}
