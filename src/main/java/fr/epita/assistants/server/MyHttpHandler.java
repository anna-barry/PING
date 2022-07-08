package fr.epita.assistants.server;

import com.sun.net.httpserver.HttpExchange;

import java.util.regex.Pattern;

public abstract class MyHttpHandler {

    protected String method;
    protected Pattern path;

    public MyHttpHandler(String method, String path) {
        this.method = method;
        this.path = Pattern.compile("^" + Utils.simplifyUrl("/" + path) + "$", Pattern.CASE_INSENSITIVE);
    }

    public MyHttpHandler(String method, Pattern path) {
        this.method = method;
        this.path = path;
    }

    public abstract Object handle(HttpExchange exchange) throws MyHttpException;

    public String getMethod() {
        return method;
    }

    public Pattern getPath() {
        return path;
    }

}