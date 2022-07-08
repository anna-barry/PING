package fr.epita.assistants.server;

import com.sun.net.httpserver.HttpExchange;

public class HelloWorld extends MyHttpHandler{
    public HelloWorld() {
        super("GET", "hello");
    }

    @Override
    public Object handle(HttpExchange exchange) throws MyHttpException {
        System.out.println("\n\n\n\nhello world\n\n\n\n");
        return null;
    }
}
