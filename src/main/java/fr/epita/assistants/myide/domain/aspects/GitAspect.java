package fr.epita.assistants.myide.domain.aspects;

import fr.epita.assistants.myide.domain.entity.Aspect;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.features.git.GitAdd;
import fr.epita.assistants.myide.domain.features.git.GitCommit;
import fr.epita.assistants.myide.domain.features.git.GitPull;
import fr.epita.assistants.myide.domain.features.git.GitPush;

import java.util.List;


public class GitAspect implements Aspect {
    @Override
    public Type getType() {
        return Mandatory.Aspects.GIT;
    }

    @Override
    public List<Feature> getFeatureList() {
        return List.of(new GitAdd(), new GitCommit(), new GitPull(), new GitPush());
    }
}
