package fr.epita.assistants.myide.domain.entity;

import fr.epita.assistants.myide.domain.entity.Aspect;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.features.ANY.*;
import fr.epita.assistants.myide.domain.features.MAVEN.Package;
import fr.epita.assistants.myide.domain.features.git.*;
import fr.epita.assistants.myide.domain.features.MAVEN.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ProjectClass implements Project {
    @NotNull private final Node rootNode;
    @NotNull private final Set<Aspect> aspects;

    @Override
    public Optional<Feature> getFeature(Feature.Type featureType) {

        if (Mandatory.Features.Any.CLEANUP.equals(featureType))
            return Optional.of(new Cleanup());
        else if (Mandatory.Features.Any.DIST.equals(featureType))
            return Optional.of(new Dist());
        else if (Mandatory.Features.Any.SEARCH.equals(featureType))
            return Optional.of(new Search());

        if (aspects.stream().anyMatch(aspect -> aspect.getType().equals(Mandatory.Aspects.GIT)))
        {
            if (Mandatory.Features.Git.COMMIT.equals(featureType))
                return Optional.of(new GitCommit());
            else if (Mandatory.Features.Git.ADD.equals(featureType))
                return Optional.of(new GitAdd());
            else if (Mandatory.Features.Git.PULL.equals(featureType))
                return Optional.of(new GitPull());
            else if (Mandatory.Features.Git.PUSH.equals(featureType))
                return Optional.of(new GitPush());
        }

        else if (aspects.stream().anyMatch(aspect -> aspect.getType().equals(Mandatory.Aspects.MAVEN)))
        {
            if (Mandatory.Features.Maven.COMPILE.equals(featureType))
                return Optional.of(new Compile());
            else if (Mandatory.Features.Maven.CLEAN.equals(featureType))
                return Optional.of(new Clean());
            else if (Mandatory.Features.Maven.TEST.equals(featureType))
                return Optional.of(new Test());
            else if (Mandatory.Features.Maven.PACKAGE.equals(featureType))
                return Optional.of(new Package());
            else if (Mandatory.Features.Maven.INSTALL.equals(featureType))
                return Optional.of(new Install());
            else if (Mandatory.Features.Maven.EXEC.equals(featureType))
                return Optional.of(new Exec());
            else if (Mandatory.Features.Maven.TREE.equals(featureType))
                return Optional.of(new Tree());
        }
        return Optional.empty();
    }

    public ProjectClass(Node rootNode, Set<Aspect> aspects) {
        this.rootNode = rootNode;
        this.aspects = aspects;
    }


    @Override
    public Node getRootNode() {
        return rootNode;
    }

    @Override
    public Set<Aspect> getAspects() {
        return aspects;
    }
}
