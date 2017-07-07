package demo;

import bb.sparkjava.BBSparkTemplate;
import demo.model.Message;
import demo.views.*;

import java.util.List;

import static spark.Spark.*;

public class DemoServer {


    public static void main(String[] args) {

        staticFileLocation("/static");

        BBSparkTemplate.init();

        //Demo messages
        Message.addMessage("Hello Ed");
        Message.addMessage("Hello Harika");
        Message.addMessage("Hello Interchan");

        get("/", (req, resp) -> {
            List<Message> allMessages = Message.getAllMessages();
            return Index.render(allMessages);
        });

        post("/", (req, resp) -> {
            //TODO add the message to the list
            List<Message> allMessages = Message.getAllMessages();
            return Index.render(allMessages);
        });

    }
}