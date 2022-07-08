package fr.epita.assistants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Timer;
import java.util.TimerTask;

public class Function {
    Statement stmt2 = null;

    Function(Statement stmt2)
    {this.stmt2 = stmt2;};

    public int get_next_nb(Statement stmt) throws SQLException {
        String sql = "SELECT * FROM Entry";

        ResultSet rs = stmt.executeQuery(sql);
        int i = 0;
        while (rs.next()) {
            i += 1;
        }
        return i;
    }

    class FunctionTimer extends TimerTask {

        private Statement stmt;


        FunctionTimer ( Statement serial )
        {
            this.stmt = serial;
        }

        @Override
        public void run() {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            /*System.out.println("Enter your text");
            String line = br.readLine();
            int length = line.length();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            int autonb = get_next_nb(this.stmt) + 1;
            System.out.println(autonb);

            String sql2 = "INSERT INTO Entry VALUES ("+autonb+", 'testfile.java', "+length+", '"+timestamp+"', 7)";

            this.stmt.execute(sql2);*/

        }
    }

    public void run()
    {
        Timer timer = new Timer ();
        FunctionTimer newft = new FunctionTimer(this.stmt2);
        //timer.schedule(newft, 0l, 1000*60*60);
        timer.schedule(newft, 0l, 1000*60*5);

    }
}
