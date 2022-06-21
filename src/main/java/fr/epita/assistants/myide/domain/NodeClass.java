package fr.epita.assistants.myide.domain;

import fr.epita.assistants.myide.domain.entity.Node;

import javax.validation.constraints.NotNull;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class NodeClass implements Node {
    @NotNull private final Path path;
    @NotNull private final Type type;
    @NotNull private final List<@NotNull Node> children;

    public NodeClass(Path path, Type type){
        this.path = path;
        this.type = type;
        this.children = new ArrayList<Node>();
    }


    @Override
    public Path getPath() {
        return this.path;
    }

    @Override
    public Type getType() {
        return this.type;
    }

    @Override
    public List<@NotNull Node> getChildren() {
        return this.children;
    }
}
