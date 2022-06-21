package fr.epita.assistants.myide.domain.features.MAVEN;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

import java.io.IOException;

public class Tree implements Feature {
    @Override
    public ExecutionReport execute(Project project, Object... params) {
        if (params.length < 1)
            return () -> false;
        try {
            ProcessBuilder b = new ProcessBuilder("mvn", "tree").directory(project.getRootNode().getPath().toFile());
            Process process = b.start();
            process.waitFor();

        } catch (IOException | InterruptedException e) {
            return () -> false;
        }

        return () -> true;
    }

    @Override
    public Type type() {
        return Mandatory.Features.Maven.TREE;
    }
}
