package fr.epita.assistants.myide.domain.features.git;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;

public class GitCommit implements Feature {
    @Override
    public ExecutionReport execute(Project project, Object... params) {
        Git i = null;
        try
        {
            i = Git.open(project.getRootNode().getPath().toFile());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return () -> false;
        }

        try
        {
            String message = (params.length > 0) ? (String) params[0] : "no message";
            i.commit().setMessage(message).call();
        }
        catch (GitAPIException e)
        {
            e.printStackTrace();
            return () -> false;
        }

        return () -> true;
    }

    @Override
    public Type type() {
        return Mandatory.Features.Git.COMMIT;
    }
}