package fr.epita.assistants.server;

public class MyHttpException extends Exception {

    private int code;

    public MyHttpException(int code, String message) {
        super(message);
        this.code = code;
    }

    public MyHttpException(int code) {
        super(MyHttpException.getMessageFromCode(code));
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static String getMessageFromCode(int code) {
        switch (code) {
            case 400:
                return "Bad request";
            default:
                return String.format("An error occurred (code: %d)", code);
        }
    }
}
