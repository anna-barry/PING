package fr.epita.assistants.myide.domain.entity;

import fr.epita.assistants.myide.domain.entity.Node;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class NodeClass implements Node {
    @NotNull private final Path path;
    @NotNull private final Type type;

    public NodeClass(Path path){
        if (path.toFile().exists()){
            this.path = path;
        }
        else {
            throw new IllegalArgumentException("Oops, can't access a non-existing path !");
        }
        if (path.toFile().isDirectory()){
            this.type = Types.FOLDER;
        }
        else if (path.toFile().isFile()){
            this.type = Types.FILE;
        }
        else{
            throw new IllegalArgumentException("Oops, the path leads to an unexpected adventure !");
        }
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
        if (isFile()){
            return new ArrayList<>();
        }
        else if (isFolder()){
            List <Node> children = new ArrayList<>();
            File[] files = path.toFile().listFiles();
            for (File file: files){
                NodeClass tmp = new NodeClass(file.toPath());
                children.add(tmp);
                if (tmp.getType() == Types.FOLDER){
                    tmp.getChildren();
                }
            }
            return children;
        }
        else{
            throw new IllegalArgumentException("Oops, can't access a non a misleading path's children");
        }
    }
}
