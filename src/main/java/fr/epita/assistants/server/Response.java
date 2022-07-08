package fr.epita.assistants.server;

import com.google.gson.Gson;

public class Response {

    private int status;
    private Object content;

    public Response(int status, Object content) {
        this.status = status;
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getJsonContent() {
        return new Gson().toJson(this.content);
    }
}
