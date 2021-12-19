package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Odpowiada za wysyłanie stringów do danego outputstream
 */
public class ServerCommunicatorOut {
    PrintWriter out;
    ServerCommunicatorOut(OutputStream outputStream) throws IOException
    {
        out = new PrintWriter(outputStream,true);
    }
    public void writeString(String string) throws IOException {
        out.println(string);
    }

}
