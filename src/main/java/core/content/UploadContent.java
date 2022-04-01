package core.content;

import java.io.Serializable;

public class UploadContent extends Content implements Serializable {
    private String uploadMessage;

    public UploadContent(String message) {
        this.uploadMessage = message;
    }

    @Override
    public void display() {

        System.out.println("'" + this.uploadMessage + "' message is saved on server");
    }

    public String getUploadMessage() {
        return uploadMessage;
    }
}
