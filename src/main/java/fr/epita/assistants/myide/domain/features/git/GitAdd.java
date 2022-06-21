package fr.epita.assistants.myide.domain.features.git;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/*
public class GitAdd implements Feature {
    @Override
    public ExecutionReport execute(Project project, Object... params) {
        try {
            File path = File.createTempFile(project.getRootNode().getPath().toString(), "");
            Files.delete(path.toPath());
            Git git = Git.open(path);
            git.push().call();
        }
        catch (IOException e) {
            return new ExecutionReport(0);
            }
        }
        return 0;
    }

    @Override
    public Type type() {
        return Mandatory.Features.Git.PUSH
    }

 */
public class GitAdd implements Feature {
    @Override
    public ExecutionReport execute(Project project, Object... params) {
        Git i = null;
        try {
            i = Git.open(project.getRootNode().getPath().toFile());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return () -> false;
        }

        String rootPath = project.getRootNode().getPath().toString();
        for (Object param : params) {
            try
            {
                Path path = Path.of(rootPath + '/' + param);
                if (!path.toFile().exists()) {
                    return () -> false;
                }
                i.add().addFilepattern((String) param).call();
            }
            catch (GitAPIException e)
            {
                e.printStackTrace();
                return () -> false;
            }
        }

        return () -> true;
    }

    @Override
    public Type type()
    {
        return Mandatory.Features.Git.ADD;
    }
}

