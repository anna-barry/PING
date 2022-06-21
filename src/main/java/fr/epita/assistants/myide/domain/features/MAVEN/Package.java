package fr.epita.assistants.myide.domain.features.MAVEN;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

public class Package implements Feature {
    @Override
    public ExecutionReport execute(Project project, Object... params) {
        try {
            ProcessBuilder b = new ProcessBuilder("mvn", "package").directory(project.getRootNode().getPath().toFile());
            Process process = b.start();
            if (params.length == 1)
            {
                Consumer<InputStream> callback = ((Package.sub) params[0]).sub;
                callback.accept(process.getInputStream());
            }
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            return () -> false;
        }

        return () -> true;
    }

    @Override
    public Type type() {
        return Mandatory.Features.Maven.PACKAGE;
    }

    public record sub(Consumer<InputStream> sub)
    {}
}
