package server;

import core.MessageType;
import core.SMPMessage;
import core.SMPSocket;
import core.content.DownloadContent;
import core.content.LoginContent;
import core.content.ResponseContent;
import core.content.UploadContent;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This module is to be used with a concurrent Echo server.
 * Its run method carries out the logic of a client session.
 *
 * @author M. L. Liu
 */

public class SMPServerThread implements Runnable {

    SMPSocket myDataSocket;

    public SMPServerThread(SMPSocket myDataSocket) {
        this.myDataSocket = myDataSocket;
    }

    public void run() {
        boolean done = false;
        SMPMessage message;
        try {
            while (!done) {
                message = myDataSocket.receiveMessage();
                /**/

                if ((message.getType()).equals(MessageType.LOGOFF)) {
                    // Session over; close the data socket.
                    System.out.println("Session over.");
                    myDataSocket.close();
                    done = true;
                } // end if
                else {

                    SMPMessage response = ServerService.createResponse(message);
                    // Now send the echo to the requestor
                    myDataSocket.sendMessage(response);
                } // end else
            } // end while !done
        } // end try
        catch (Exception ex) {
            System.out.println("Exception caught in thread: " + ex);
        } // end catch
    } // end run


} // end class
