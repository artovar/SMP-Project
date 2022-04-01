package server;

import core.MessageType;
import core.SMPMessage;
import core.content.DownloadContent;
import core.content.LoginContent;
import core.content.ResponseContent;
import core.content.UploadContent;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ServerService {


    private static List<String> registeredUsers = Arrays.asList(
            "user:Alex,password:123123",
            "user:Guille,password:321321",
            "user:Alexito,password:itoito"
    );

    public static SMPMessage createResponse(SMPMessage request) {
        SMPMessage response;
        try {
            Thread.sleep(1000);
            switch (request.getType()) {
                case DOWNLOAD:
                    //Load the log.txt file
                    System.out.println("Download request accepted");
                    File myObj = new File("log.txt");
                    Scanner myReader = new Scanner(myObj);
                    ArrayList<String> list = new ArrayList<String>();
                    while (myReader.hasNextLine()) {

                        list.add(myReader.nextLine());
                    }
                    response = new SMPMessage(request.getType(), new DownloadContent(list));
                    return response;

                case LOGIN:
                    LoginContent requestContent = (LoginContent) request.getContent();
                    MessageType responseType;
                    ResponseContent responseContent;

                    if (checkUserAndPassword(requestContent)) {
                        responseType = MessageType.OK;
                        responseContent = new ResponseContent("User logged!");
                    } else {
                        responseType = MessageType.ERROR;
                        responseContent = new ResponseContent("Incorrect user or password");
                    }

                    response = new SMPMessage(responseType, responseContent);
                    return response;
                case UPLOAD:

                    String message = ((UploadContent) request.getContent()).getUploadMessage();
                    System.out.println("Saving message: " + message);
                    FileWriter fileWriter = new FileWriter("log.txt", true);
                    PrintWriter printWriter = new PrintWriter(fileWriter);
                    printWriter.print(message + "\n");
                    printWriter.close();
                    response = new SMPMessage(MessageType.OK, new ResponseContent("Message saved successfully"));

                    return response;

                case LOGOFF:

                    return new SMPMessage(MessageType.LOGOFF, new ResponseContent("Session Ended"));

                default:

                    return new SMPMessage(MessageType.ERROR, new ResponseContent("Incorrect message type sent"));

            }

        } catch (FileNotFoundException e) {
            System.out.println("No messages stored");
            return new SMPMessage(MessageType.ERROR, new ResponseContent("There is no messages stored"));

        } catch (IOException e) {
            //e.printStackTrace();
            return new SMPMessage(MessageType.ERROR, new ResponseContent(e.getMessage()));

        } catch (InterruptedException e) {
            //e.printStackTrace();
            return new SMPMessage(MessageType.ERROR, new ResponseContent(e.getMessage()));
        }
    }

    public static boolean checkUserAndPassword(LoginContent requestContent) {

        return registeredUsers.contains("user:" + requestContent.getUser() + ",password:" + requestContent.getPassword());

    }


}
