package fr.epita.assistants.myide.domain.features.MAVEN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class help4stream  extends Thread {


    private InputStream IS;

    public help4stream(InputStream IS) {
        this.IS = IS;
    }

    public void run(boolean err) {
        try {
            final Duration timeout = Duration.ofSeconds(8);
            ExecutorService exec = Executors.newSingleThreadExecutor();

            final Future<String> handler = exec.submit(new Callable() {
                @Override
                public String call() throws Exception {
                    String line = null;
                    InputStreamReader reader = new InputStreamReader(IS);
                    BufferedReader br = new BufferedReader(reader);
                    while ((line = br.readLine()) != null) {

                        if (err)
                            System.err.println("Err:" + line);
                        else
                            System.out.println(line);
                    }
                    return "";
                }
            });

            try {
                handler.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
            } catch (TimeoutException e) {
                handler.cancel(true);
            }
            exec.shutdownNow();
        } catch (Exception e) {
            System.err.println(e);
        }

    }
}
