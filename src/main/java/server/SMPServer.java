package server;

import core.SMPSocket;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;

public class SMPServer {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int serverPort = 8080; // default port
        String message;

        if (args.length == 1)
            serverPort = 8080;
        try {
            // Setting TLS properties

            System.setProperty("jdk.tls.server.protocols", "TLSv1.2");
            System.setProperty("jdk.tls.client.protocols", "TLSv1.2");


            //SSL Protection
            String ksName = "herong.jks";
            char ksPass[] = "password".toCharArray();
            char ctPass[] = "password".toCharArray();


            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(ksName), ksPass);

            KeyManagerFactory kmf =
                    KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, ctPass);
            SSLContext sc = SSLContext.getInstance("TLSv1.2");
            sc.init(kmf.getKeyManagers(), null, null);
            SSLServerSocketFactory ssf = sc.getServerSocketFactory();
            SSLServerSocket myConnectionSocket
                    = (SSLServerSocket) ssf.createServerSocket(serverPort);

            printServerSocketInfo(myConnectionSocket);

            //SSL ended

            /**/
            System.out.println("Short Message Protocol server ready.");
            while (true) { // forever loop
                // wait to accept a connection
                /**/
                System.out.println("Waiting for a connection.");
                SMPSocket myDataSocket = new SMPSocket((SSLSocket) myConnectionSocket.accept());

                printSocketInfo(myDataSocket.getSocket());
                /**/
                System.out.println("connection accepted");
                // Start a thread to handle this client's sesson
                Thread theThread = new Thread(new SMPServerThread(myDataSocket));
                theThread.start();
                // and go on to the next client

            } // end while forever
        } // end try
        catch (Exception ex) {
            ex.printStackTrace();
        } // end catch
    }

    private static void printSocketInfo(SSLSocket s) {
        System.out.println("CONNECTION STABLISHED");
        System.out.println("----------------------------------------");
        System.out.println("Socket class: " + s.getClass());
        System.out.println("   Remote address = "
                + s.getInetAddress().toString());
        System.out.println("   Remote port = " + s.getPort());
        System.out.println("   Local socket address = "
                + s.getLocalSocketAddress().toString());
        System.out.println("   Local address = "
                + s.getLocalAddress().toString());
        System.out.println("   Local port = " + s.getLocalPort());
        System.out.println("   Need client authentication = "
                + s.getNeedClientAuth());
        SSLSession ss = s.getSession();
        System.out.println("   Cipher suite = " + ss.getCipherSuite());
        System.out.println("   Protocol = " + ss.getProtocol());
        System.out.println("----------------------------------------");
    }


    private static void printServerSocketInfo(SSLServerSocket s) {
        System.out.println("OPENING SERVER");
        System.out.println("----------------------------------------");
        System.out.println("Server socket class: " + s.getClass());
        System.out.println("   Socket address = "
                + s.getInetAddress().toString());
        System.out.println("   Socket port = "
                + s.getLocalPort());
        System.out.println("   Need client authentication = "
                + s.getNeedClientAuth());
        System.out.println("   Want client authentication = "
                + s.getWantClientAuth());
        System.out.println("   Use client mode = "
                + s.getUseClientMode());
        System.out.println("----------------------------------------\n");

    }

}
