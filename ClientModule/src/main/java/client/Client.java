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
    private Hex.State pegsColor;
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
        clientFrame.setSize(800,800);//temp
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
     * Odświerza stan wyświetlanej planszy
     */
    public void updateFrameBoard(){
        clientFrame.updateBoard();
    }
    /**
     * Główna metoda. Najpierw prosi o stan planszy, potem przyjmuje polecenia
     * @throws IOException od socket.close() nie wiem czy to będzie działać
     */
    public void play() throws IOException {
        commandReader.fetchInstruction(); // przyjmowanie przypisanego koloru
        clientFrame.setTitle("Gracz " + pegsColor.name());
        commandWriter.requestBoardState();
        commandReader.fetchInstruction();
        clientFrame.updateBoard();
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

    public Hex.State getPegsColor()
    {
        return  pegsColor;
    }
    public void setPegsColor(Hex.State color)
    {
        pegsColor = color;
    }



}
