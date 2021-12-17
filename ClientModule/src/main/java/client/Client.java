package client;

import client.gui.ClientFrame;
import hex.Hex;

import java.io.IOException;
import java.net.Socket;

/**
 * Klasa klienta commandReader odczytuje komendy, commandWriter wysyła komendy
 */
public class Client {
    private static Socket socket;
    private int playerId;
    private ClientFrame clientFrame;
    private boolean isActive;
    public CommandReader commandReader;
    public CommandWriter commandWriter;
    public ClientGameState gameState;


    public Client() {
        this.clientFrame = new ClientFrame(this);
        this.gameState = new ClientGameState();
        this.commandReader = new CommandReader(this);
        this.commandWriter = new CommandWriter(this);
        clientFrame.setSize(400,400);//temp
        clientFrame.setVisible(true);//temp
        this.isActive = true;
    }

    /**
     * konfiguracja commandWriter, commandReader
     * @param socket socket
     */
    public void setCommunication(Socket socket) {
        Client.socket = socket;
        commandWriter.setConnection(socket);
        commandReader.setConnection(socket);
    }

    /**
     * Odświerza stan lokalnej planszy
     * @param array tablica Hex[][]
     */
    public void updateBoard(Hex[][] array) {
        gameState.setHexes(array);
    }

    /**
     * Główna metoda. Najpierw prosi o stan planszy, potem przyjmuje polecenia
     * @throws IOException od socket.close() nie wiem czy to będzie działać
     */
    public void play() throws IOException {
        try {
            while (commandReader.hasNext()) {
                commandReader.fetchInstruction();
            }
            commandWriter.quit();
        }

    catch (Exception e) {
        e.printStackTrace();
        } finally {
            socket.close();
            clientFrame.dispose();
        }
    }

}
