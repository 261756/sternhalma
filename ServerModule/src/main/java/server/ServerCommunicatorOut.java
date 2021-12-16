package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ServerCommunicatorOut {
    ObjectOutputStream out;
    ServerCommunicatorOut(OutputStream outputStream) throws IOException
    {
        out = new ObjectOutputStream(outputStream);
    }
    public void writeObject(Object obj) throws IOException {
        out.writeObject(obj);
    }

}
