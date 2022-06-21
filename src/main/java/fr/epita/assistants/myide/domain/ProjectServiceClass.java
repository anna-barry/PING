package fr.epita.assistants.myide.domain;

import fr.epita.assistants.myide.domain.entity.Aspect;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.service.NodeService;
import fr.epita.assistants.myide.domain.service.ProjectService;

import java.io.File;
import java.lang.invoke.DelegatingMethodHandle$Holder;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProjectServiceClass implements ProjectService
{

    private NodeService Nodeservice;

    public ProjectServiceClass(NodeService node)
    {
        this.Nodeservice = node;
    }

    @Override
    public Project load(Path root) {
        File file_or_folder = new File(String.valueOf(root));
        Node.Types type = Node.Types.FILE;
        if (file_or_folder.isDirectory())
        {
            type = Node.Types.FOLDER;
        }
        Node racine = new NodeClass(root, type);
        //this.Nodeservice = new NodeServiceClass();
        int[] tab = new int[3];
        tab[0] = 1;
        int[] tab1 = arbre(root, racine, tab);

        return new ProjectClass(racine, null);
    }

    private int[] arbre(Path root, Node racine, int[] tab)
    {
        File file_or_folder = new File(String.valueOf(root));
        if (!file_or_folder.isDirectory())
            return tab;
        File[] liste = file_or_folder.listFiles();
        Node inter_racine = null;
        for (File e : liste)
        {
            Node.Types type = Node.Types.FILE;
            String chemin = root.toString() + "/" + e.getName();
            root = Paths.get(chemin);
            if (chemin.matches(".+[.]git"))
                tab[1] = 1;
            if (chemin.matches(".+[.]xml"))
                tab[2] = 1;
            if (file_or_folder.isDirectory()) {
                type = Node.Types.FOLDER;
                inter_racine = new NodeClass(root, type);
                racine.getChildren().add(inter_racine);
                return arbre(root, inter_racine, tab);
            }
            else  {
                inter_racine = new NodeClass(root, type);
                racine.getChildren().add(inter_racine);
            }
        }
        return tab;
    }

    @Override
    public Feature.ExecutionReport execute(Project project, Feature.Type featureType, Object... params) {
        return null;
    }

    @Override
    public NodeService getNodeService() {
        return this.Nodeservice;
    }
}
