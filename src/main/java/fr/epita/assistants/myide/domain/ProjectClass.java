package fr.epita.assistants.myide.domain;

import fr.epita.assistants.myide.domain.entity.Aspect;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.Project;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.Set;

public class ProjectClass implements Project {
    @NotNull private final Node rootNode;
    @NotNull private final Set<Aspect> aspects;

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
