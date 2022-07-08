package fr.epita.assistants.server;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static String simplifyUrl(String path) {
        Pattern pattern = Pattern.compile("/+");
        Matcher matcher = pattern.matcher(path + "/");
        return matcher.replaceAll("/");
    }

    public static Object serialize(Object data) {
        try {
            return new Gson().toJson(data);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T deserialize(String data, Class<T> tClass) {
        try {
            return new Gson().fromJson(data, tClass);
        } catch (Exception e) {
            return null;
        }
    }

    public static String read(InputStream inputStream) {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            int current;
            StringBuilder body = new StringBuilder();
            while ((current = bufferedReader.read()) != -1) {
                body.append((char) current);
            }

            bufferedReader.close();
            inputStreamReader.close();

            return body.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}