package fr.epita.assistants.myide.domain.service;

import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.NodeClass;
import fr.epita.assistants.myide.domain.service.NodeService;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.io.File;

public class NodeServiceClass implements NodeService {

    @Override
    public Node update(Node node, int from, int to, byte[] insertedContent) {
        if (from < 0 || to < 0){
            throw new IllegalArgumentException("Oops, can't access negative values !");
        }
        if (node.getType() == Node.Types.FOLDER){
            throw new IllegalArgumentException("Oops, can't update a folder's content !");
        }
        if (to < from){
            throw new IllegalArgumentException("Oops, you switched your 'from' and 'to' !");
        }
        File file = node.getPath().toFile();
        byte[] content;
        try (FileInputStream fileinput = new FileInputStream(file)){
            content = fileinput.readAllBytes();
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }
        try (FileOutputStream fileoutput = new FileOutputStream(file)){
            if (from != 0){
                fileoutput.write(content, 0, from);
            }
            fileoutput.write(insertedContent, from, to);
            if (to != content.length){
                fileoutput.write(content, to, content.length);
            }
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return node;
    }

    @Override
    public boolean delete(Node node) {
        if (node.getType() == Node.Types.FILE) {
            File file = node.getPath().toFile();
            if (file.delete() == true){
                return true;
            }
        }
        else {
            File[] file = node.getPath().toFile().listFiles();
            for (File a: file){
                if (a.delete() == false){
                    return false;
                }
            }
            File nodeAsFile = node.getPath().toFile();
            if (nodeAsFile.delete() == true){
                return true;
            }
        }
        return false;
    }


    @Override
    public Node create(Node folder, String name, Node.Type type) {
        String newFolder = folder.getPath().toString() + "/" + name;
        Path newFolderPath = Path.of(newFolder);
        if (folder.getType() != Node.Types.FOLDER){
            throw new IllegalArgumentException("Oops, can only give children to a folder !");
        }
        for (Node i: folder.getChildren()){
            if (i.getPath().toString() == newFolder){
                throw new IllegalArgumentException("Oops, it already exists !");
            }
        }
        try {
            File file = new File(newFolder);
            if (type == Node.Types.FOLDER){
                if (file.mkdir() == false){
                    throw new RuntimeException("Oops, couldn't create that folder");
                }
            }
            else{
                file.createNewFile();
            }
        }
        catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException("Oops, can't create the new Node !");
        }
        NodeClass res = new NodeClass(newFolderPath, type);
        return res;
    }

    @Override
    public Node move(Node nodeToMove, Node destinationFolder) {
        Path nodeToMovePath = nodeToMove.getPath();
        Path newNodeToMove = Path.of(destinationFolder.getPath().toString() + '/' + nodeToMovePath.getFileName()
                .toString());
        try {
            if (nodeToMove.isFolder())
                FileUtils.moveDirectory(nodeToMovePath.toFile(), newNodeToMove.toFile());
            else
                Files.move(nodeToMovePath, newNodeToMove);
        } catch (FileAlreadyExistsException e) {
            throw new RuntimeException("A file already exists with that name");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't move directory");
        }
        return new NodeClass(newNodeToMove, null);
    }
}
