package client;

import core.MessageType;
import core.SMPMessage;
import core.SMPSocket;
import core.content.ResponseContent;

import javax.net.ssl.*;
import java.net.*;
import java.io.*;

public class SMPClientHelper {


    static final SMPMessage endMessage = new SMPMessage(MessageType.LOGOFF, new ResponseContent("Session Ended"));
    private SMPSocket mySocket;
    private InetAddress serverHost;
    private int serverPort;

    SMPClientHelper(String hostName,
                    String portNum) throws
            IOException {

        this.serverHost = InetAddress.getByName(hostName);
        this.serverPort = Integer.parseInt(portNum);
        //Instantiates a stream-mode socket and wait for a connection.

        //SSL layer Handshake
        SSLSocket c = SSLConnection();
        this.mySocket = new SMPSocket(c);
        /**/
        System.out.println("Connection request made");


    } // end constructor


    public SMPMessage sendAndReceive(SMPMessage message) throws SocketException,
            IOException, ClassNotFoundException {

        SMPMessage echo;
        try {
            mySocket.sendMessage(message);
            this.mySocket.getSocket().setSoTimeout(1500);
            echo = mySocket.receiveMessage();
        } catch (Exception e) {

            System.out.println("Server has been reset");
            System.out.println("Reconnecting...");
            SSLSocket c = SSLConnection();

            this.mySocket = new SMPSocket(c);

            echo = sendAndReceive(message);

        }

        return echo;
    } // end getEcho

    public SSLSocket SSLConnection() throws SocketException {
        SSLSocket c;
        try {
            SSLSocketFactory f = (SSLSocketFactory) SSLSocketFactory.getDefault();
            c = (SSLSocket) f.createSocket(this.serverHost, this.serverPort);
            c.startHandshake();
            //ClientService.printSocketInfo(c);
            return c;
        } catch (Exception e) {

            throw new SocketException();
        }

    }

    public void done() throws SocketException,
            IOException {
        mySocket.sendMessage(endMessage);
        mySocket.close();
    } // end done
    //end class
}
