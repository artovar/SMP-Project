package core.content;

import core.content.Content;

import java.io.Serializable;

public class LoginContent extends Content implements Serializable {

    private String user;
    private String password;

    public LoginContent(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return this.user;
    }

    public String getPassword() {
        return this.password;
    }


    @Override
    public void display() {
        System.out.println("Log in Successfull");
    }


}
