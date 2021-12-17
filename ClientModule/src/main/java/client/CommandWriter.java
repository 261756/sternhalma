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

    /**
     * Powiadamia serwer o wyjściu użytkownika
     */
    public void quit()
    {
        printWriter.println("quit");
    }
    public void move(int x1, int y1, int x2, int y2)
    {
        printWriter.println("requestMove" + x1 + " " + y1 + " " + x2 + " " + y2);
    }

}
