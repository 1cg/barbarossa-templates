package demo;

import bb.BBTemplates;
import bb.sparkjava.BBSparkTemplate;
import demo.model.Message;
import demo.views.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import static spark.Spark.*;

public class DemoServer {


    public static void main(String[] args) {

        staticFileLocation("/static");

        BBSparkTemplate.init();
        BBTemplates.trace();

        get("/", (req, resp) -> {
            if (req.session().attribute("userName") == null) {
                resp.redirect("/login");
                return null;
            } else {
                return dumpThreadsOnException(()-> Index.render(Message.getAllMessages(), req.session().attribute("userName")));
            }
        });

        get("/login", (req, resp) -> dumpThreadsOnException(()-> Login.render()));

        post("/login", (req, resp) -> {
            String login = req.queryParams("userName");
            if (login.length() > 0) {
                req.session().attribute("userName", login);
                Message.setUser(req.session().attribute("userName"));
                resp.redirect("/");
            } else {
                resp.redirect("/login");
            }
            return null;
        });

        get("/messages", (req, resp) -> Index.messageBox.render(Message.getAllMessages()));
        post("/messages", (req, resp) -> {
            String message = req.queryParams("message");
            if (message != null && message.length() > 0) {
                Message.addMessage(req.session().attribute("userName"), message);
            }
            return Index.inputForm.render();
        });

        get("/who", (req, resp) -> dumpThreadsOnException(() -> Index.who.render()));
    }

    public static <V> V dumpThreadsOnException(Callable<V> logic) {
        try {
            return logic.call();
        } catch (Exception e) {
            Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
            for (Thread thread : allStackTraces.keySet()) {
                System.out.print("Thread " + thread.getName());
                if(Thread.currentThread() == thread) {
                    System.out.println("[CURRENT THREAD]");
                }
                System.out.println("");
                System.out.println("  ContextClassloader: " + thread.getContextClassLoader());
                System.out.println("               State: " + thread.getState());
                System.out.println("  StackTrace");
                for (StackTraceElement stackTraceElement : allStackTraces.get(thread)) {
                    System.out.println("    " + stackTraceElement.toString());
                }
            }
            throw new RuntimeException(e);
        }
    }
}
