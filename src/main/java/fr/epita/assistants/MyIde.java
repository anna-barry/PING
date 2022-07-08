package fr.epita.assistants;

import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.service.ProjectService;
import fr.epita.assistants.myide.domain.service.ProjectServiceClass;
import fr.epita.assistants.server.AnyCleanUp;
import fr.epita.assistants.server.HelloWorld;
import fr.epita.assistants.server.Timing;
import fr.epita.assistants.server.maven.*;
import fr.epita.assistants.server.maven.PackageM;
import fr.epita.assistants.utils.Given;
import fr.epita.assistants.server.MyHttpServer;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Starter class, we will use this class and the init method to get a
 * configured instance of {@link ProjectService}.
 */
@Given(overridden = false)
public class MyIde {
    public static Project p = null;

    /**
     * Init method. It must return a fully functional implementation of {@link ProjectService}.
     *
     * @return An implementation of {@link ProjectService}.
     */
    public static ProjectService init(final Configuration configuration) {
        ProjectServiceClass projectService = new ProjectServiceClass();
        //FIXME: Configuration
        return projectService;
    }

    /**
     * Record to specify where the configuration of your IDE
     * must be stored. Might be useful for the search feature.
     */
    public record Configuration(Path indexFile,
                                Path tempFolder) {
    }

    public static void main(String[] args) throws IOException {
        ProjectService debut = init(null);
        //p = debut.load(Path.of("/Users/anna/Pictures/ING1/S6/ping/tmp/PING/src/main/java/fr/epita/assistants/MyIde.java"));
        p = debut.load(Path.of("/Users/anna/Pictures/ING1/S6/ping/tmp/PING/"));
        //testLink testing = new testLink();
        //testing.runtest();
        //get("/hello", (req, res) -> "Hello World");

        final String JDBC_DRIVER = "org.h2.Driver";
        final String DB_URL = "jdbc:h2:./resources/HR";

        //  Database credentials
        Connection conn = null;
        Statement stmt = null;

        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
            conn = DriverManager.getConnection(DB_URL);

            // Execute a query
            stmt = conn.createStatement();

            String create_table =
                    "CREATE TABLE IF NOT EXISTS ENTRY (\n" +
                            "         ID INTEGER  PRIMARY KEY AUTO_INCREMENT,\n" +
                            "         NBERRORS INTEGER \n"+
                            ");"
                    ;
            stmt.execute(create_table);
            Function fn = new Function(stmt);
            Thread newThread = new Thread(() -> {
                fn.run();
            });
            newThread.start();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (ClassNotFoundException e) {
            System.out.println(e.toString());
        }

        MyHttpServer s = new MyHttpServer("127.0.0.1", 4000);
        s.registerHandler(new HelloWorld());
        s.registerHandler(new AnyCleanUp());
        s.registerHandler(new CleanM());
        s.registerHandler(new CompileM());
        s.registerHandler(new ExecM());
        s.registerHandler(new InstallM());
        s.registerHandler(new PackageM());
        s.registerHandler(new TestM());
        s.registerHandler(new TreeM());


        s.start();
    }

}
