package core.content;

import core.content.Content;

import java.io.Serializable;
import java.util.ArrayList;

public class DownloadContent extends Content implements Serializable {

    private ArrayList<String> messageList;

    public DownloadContent(ArrayList<String> messageList) {
        this.messageList = messageList;
    }

    @Override
    public void display() {

        System.out.println("All messages on server:");
        System.out.println("-----------------------");
        messageList.forEach((s) -> System.out.println(s));
        System.out.println("-----------------------");
    }

}
