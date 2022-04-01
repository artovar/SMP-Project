package client;

import core.*;
import core.content.DownloadContent;
import core.content.EmptyContent;
import core.content.LoginContent;
import core.content.UploadContent;

import java.io.*;
import java.net.SocketException;

/**
 * This module contains the presentaton logic of an SMP Client.
 *
 * @author Alejandro Arenas
 */
public class SMPClient {
    static final String endMessage = "2";

    public static void main(String[] args) {

        System.setProperty("javax.net.ssl.trustStore", "public.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "password");
        System.setProperty("jdk.tls.server.protocols", "TLSv1.2");
        System.setProperty("jdk.tls.client.protocols", "TLSv1.2");

        InputStreamReader is = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(is);
        String hostName, portNum;
        try {
            System.out.println("Welcome to the Short Message Protocol client.\n");
            hostName = "localhost";  //   use the default host name
            portNum = "8080";          // default port number
            SMPClientHelper helper =
                    new SMPClientHelper(hostName, portNum);
            boolean done = false, loged = false;
            SMPMessage message = null, response;

            loged = login(br, helper);

            while (!loged) {
                System.out.println("Please Login with a correct user and password :)");
                loged = login(br, helper);
            }

            while (!done) {
                System.out.println("Enter a line to receive an echo "
                        + "from the server, or a single period to quit.");

                System.out.println("What do you want to do?");

                System.out.println("UPLOAD[0]    DOWNLOAD[1]     LOG-OFF[2]");
                String type = br.readLine();

                if ((type.trim()).equals(endMessage)) {
                    done = true;
                    helper.done();
                } else {

                    switch (type) {
                        case "0":

                            System.out.println("Write your message to upload:");
                            String uploadMessage = br.readLine();
                            message = new SMPMessage(MessageType.UPLOAD, new UploadContent(uploadMessage));
                            break;
                        case "1":
                            System.out.println("Sending download request...");
                            message = new SMPMessage(MessageType.DOWNLOAD, new EmptyContent());
                            break;
                        default:
                            System.out.println("Please, select a correct option.");
                            continue;
                    }

                    response = helper.sendAndReceive(message);

                    System.out.println();
                    response.getContent().display();
                    System.out.println();
                }

            } // end while
        } // end try
        catch (SocketException ex) {
            System.out.println("These host is closed");
        } //end catch
        catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    } //end main


    public static boolean login(BufferedReader br, SMPClientHelper helper) throws IOException, ClassNotFoundException {

        SMPMessage message;
        SMPMessage response;
        System.out.println("Write your user:");
        String user = br.readLine();
        System.out.println("Write your password:");
        String passw = br.readLine();
        message = new SMPMessage(MessageType.LOGIN, new LoginContent(user, passw));

        response = helper.sendAndReceive(message);

        response.getContent().display();

        return response.getType().equals(MessageType.OK);

    }


} // end class







