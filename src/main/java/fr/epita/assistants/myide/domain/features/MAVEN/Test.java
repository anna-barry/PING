package fr.epita.assistants.myide.domain.features.MAVEN;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

import java.io.IOException;

public class Test implements Feature {
    @Override
    public ExecutionReport execute(Project project, Object... params) {
         try {
            ProcessBuilder b = new ProcessBuilder("mvn", "test").directory(project.getRootNode().getPath().toFile());
            Process process = b.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            return () -> false;
        }

        return () -> true;
    }

    @Override
    public Type type() {
        return Mandatory.Features.Maven.TEST;
    }
}
