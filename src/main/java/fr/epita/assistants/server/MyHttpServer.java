package fr.epita.assistants.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class MyHttpServer {

    private String host;
    private int port;
    private HttpServer server;
    private List<MyHttpHandler> handlers;

    public MyHttpServer(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        this.server = HttpServer.create(new InetSocketAddress(this.host, this.port), 0);
        this.handlers = new ArrayList<>();
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public HttpServer getServer() {
        return server;
    }

    public List<MyHttpHandler> getHandlers() {
        return handlers;
    }

    public void registerHandler(MyHttpHandler handler) {
        System.out.println(String.format("Registered %s: %s", handler.getPath().toString(), handler.getMethod()));
        this.handlers.add(handler);
    }

    public void start() {
        System.out.println("Context creation...");
        this.server.createContext("/", exchange -> {
            System.out.println(exchange.getRequestURI().toString());
            Response response = new Response(404, new JsonObject().add("detail", "Endpoint not found.").getObject());

            String url = Utils.simplifyUrl(exchange.getRequestURI().toString());
            System.out.println(exchange.getRequestMethod() + ": " + url);

            for (MyHttpHandler handler : this.handlers) {
                if (handler.getPath().matcher(url).find()) {
                    if (exchange.getRequestMethod().equalsIgnoreCase(handler.getMethod())) {
                        try {
                            response = new Response(200, handler.handle(exchange));
                        } catch (MyHttpException exception) {
                            response = new Response(exception.getCode(), exception.getMessage());
                        }
                        break;
                    }

                    response = new Response(405, "Method not allowed.");
                }
            }

            // Headers
            exchange.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, DELETE, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Origin, Content-Type, X-Auth-Token");
            exchange.sendResponseHeaders(/*response.getStatus()*/ 200, response.getJsonContent().length());

            // Writing body
            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(response.getJsonContent().getBytes());
            outputStream.close();
        });

        System.out.println("Starting server...");
        this.server.start();
    }
}