package fr.epita.assistants.myide.domain.service;

import fr.epita.assistants.myide.domain.aspects.AnyAspect;
import fr.epita.assistants.myide.domain.aspects.GitAspect;
import fr.epita.assistants.myide.domain.aspects.MavenAspect;
import fr.epita.assistants.myide.domain.entity.NodeClass;
import fr.epita.assistants.myide.domain.entity.ProjectClass;
import fr.epita.assistants.myide.domain.entity.*;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Optional;

public class ProjectServiceClass implements ProjectService
{

    NodeService nodeService = new NodeServiceClass();

    @Override
    public Project load(Path root)
    {
        File file_or_folder = new File(String.valueOf(root));
        Node.Types type = Node.Types.FILE;
        if (file_or_folder.isDirectory())
        {
            type = Node.Types.FOLDER;
        }
        Node racine = new NodeClass(root, type);
        List<Aspect> aspects = new ArrayList<>();
        // Get the type of the project
        List<Node> children = racine.getChildren();

        aspects.add(new AnyAspect());
        if (children.stream().anyMatch(node -> node.getPath().getFileName().toString().equals("pom.xml")))
            aspects.add(new MavenAspect());
        if (children.stream().anyMatch(node -> node.getPath().getFileName().toString().equals(".git")))
            aspects.add(new GitAspect());

        ProjectClass project = new ProjectClass(racine, Set.copyOf(aspects));
        return project;
    }


    @Override
    public Feature.ExecutionReport execute(Project project, Feature.Type featureType, Object... params) {
        Optional<Feature> feature = project.getFeature(featureType);
        if (feature.isEmpty())
            return () -> false;
        return feature.get().execute(project, params);
    }

    @Override
    public NodeService getNodeService() {
        return this.nodeService;
    }
}
