package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Scanner;

/**
 * Odpowiada za przyjmowanie Stringów z danego inputStream
 */
public class ServerCommunicatorIn {

    Scanner in;
    ServerCommunicatorIn(InputStream inputStream)
    {
        in = new Scanner(inputStream);
    }
    Boolean availableCommandFromClient()
    {
        return in.hasNextLine();
    }
    String getCommandFromClient()
    {
        return in.nextLine();
    }


}
