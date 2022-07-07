package fr.epita.assistants;

import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.service.ProjectService;
import fr.epita.assistants.myide.domain.service.ProjectServiceClass;
import fr.epita.assistants.utils.Given;

import java.nio.file.Path;
import fr.epita.assistants.myide.domain.features.testLink;

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

    public static void main(String[] args)
    {
        ProjectService debut = init(null);
        p = debut.load(Path.of("/Users/anna/Documents/EPITA/git/PING"));
        testLink testing = new testLink();
        testing.runtest();

    }

}
