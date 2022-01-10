package server.gui;

public class ServerLogTerminal implements ServerLog{

    @Override
    public void log(String msg) {
        System.out.println(msg);
    }
}
