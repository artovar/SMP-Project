package core;

import core.content.EmptyContent;
import core.content.ResponseContent;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class SMPSocket {

    private final SSLSocket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;


    public SMPSocket(SSLSocket socket) {
        this.socket = socket;
        setStreams();
    }


    public SSLSocket getSocket() {
        return this.socket;
    }

    private void setStreams() {
        try {
            OutputStream outStream = this.socket.getOutputStream();
            // create a PrinterWriter object for character-mode output
            this.output =
                    new ObjectOutputStream(outStream);
            // get an input stream for reading from the data socket
            InputStream inStream = this.socket.getInputStream();
            this.input =
                    new ObjectInputStream(inStream);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendMessage(SMPMessage message)
            throws IOException {

        this.output.writeObject(message);

        //The ensuing flush method call is necessary for the data to
        // be written to the socket data stream before the
        // socket is closed.
        //this.output.flush();
    } // end sendMessage

    public SMPMessage receiveMessage()
            throws IOException, ClassNotFoundException {
        // read a line from the data stream

        return (SMPMessage) input.readObject();

    }//end receiveMessage

    public void close()
            throws IOException {
        this.socket.close();
    }
}
