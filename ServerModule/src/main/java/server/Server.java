package server;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;

/**
 * OBSŁUGIWANE KOMENDY:
 * requestHexes: odpowiada "sendingHexes", wysyła tablicę Hexów
 */
public class Server {
    private static GameState GS;
    public Server() throws Exception {
        GS = new GameState();
        startServer();

    }
    public void startServer() throws Exception {
        try (var listener = new ServerSocket(59898)) {
            System.out.println("The capitalization server is running...");
            var pool = Executors.newFixedThreadPool(20);
            while (true) {
                pool.execute(new Capitalizer(listener.accept()));
            }
        }
    }

    private static class Capitalizer implements Runnable {
        private Socket socket;

        Capitalizer(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("Connected: " + socket);
            try {
                OutputStream outputStream = socket.getOutputStream();
                var in = new Scanner(socket.getInputStream());
                /**
                 * kiedy używamy outObject outString przestaje działać, dlatego Stringi są wysyłane także jako obiekt.
                 */
                //var outString = new PrintWriter(outputStream, true);
                ObjectOutputStream outObject = new ObjectOutputStream(outputStream);
                while (in.hasNextLine()) {
                    //System.out.println(in.nextLine());
                    if (in.nextLine().equals("requestHexes"))
                    {
                        outObject.writeObject("sendingHexes");
                        outObject.writeObject(GS.getHexes());
                    }
                    else
                    {
                        outObject.writeObject("unknownCommand");
                    }

                }
            } catch (Exception e) {
                System.out.println("Error:" + socket);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                }
                System.out.println("Closed: " + socket);
            }
        }
    }

}
