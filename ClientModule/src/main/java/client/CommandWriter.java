package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Klasa wysyłająca odpowiednie polecenia na serwer
 */
public class CommandWriter {
    Client client;
    PrintWriter printWriter;
    public CommandWriter(Client client) {
        this.client = client;
    }

    /**
     * Ustawia połączenie
     * @param socket socket
     */
    public void setConnection(Socket socket) {
        try {
            this.printWriter = new PrintWriter(socket.getOutputStream(),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prosi o stan planszy
     */
    public void requestBoardState() {
        printWriter.println("requestHexes");
    }
}
