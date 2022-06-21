package fr.epita.assistants.myide.domain;

import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.service.NodeService;

import java.io.File;
import java.util.List;
import java.io.File;

public class NodeServiceClass implements NodeService {

    @Override
    public Node update(Node node, int from, int to, byte[] insertedContent) {

        return;
    }

    @Override
    public boolean delete(Node node) {
        if (node.getType() == Node.Types.FILE) {
            File file = new File(node.getPath().normalize().toString());
            if (file.delete() == true){
                return true;
            }
            var a = 0;
        }
        else {
            List<Node> children = node.getChildren();
            for (Node a:children){
                if (delete(a) == false){
                    return false;
                }
            }
            File file = new File(node.getPath().normalize().toString());
            if (file.delete() == true){
                return true;
            }
        }
        return false;
    }

    @Override
    public Node create(Node folder, String name, Node.Type type) {

        return null;
    }

    @Override
    public Node move(Node nodeToMove, Node destinationFolder) {
        return null;
    }
}
