package core;

import core.content.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SMPMessage implements Serializable {


    private final MessageType type;
    private final Content content;

    public SMPMessage(MessageType type, Content content) {
        this.type = type;
        this.content = content;
    }

    public MessageType getType() {
        return this.type;
    }

    public Content getContent() {
        return this.content;
    }


}
