package core.content;

import java.io.Serializable;

public class ResponseContent extends Content implements Serializable {

    private String response;

    public ResponseContent(String response) {
        this.response = response;
    }

    @Override
    public void display() {
        System.out.println(response);
    }

}
