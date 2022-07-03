package fr.epita.assistants.myide.domain.service;

import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.NodeClass;
import fr.epita.assistants.myide.domain.service.NodeService;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.io.File;

public class NodeServiceClass implements NodeService {

    @Override
    @SneakyThrows
    public Node update(Node node, int from, int to, byte[] insertedContent) {
        if (!node.isFile()) {
            throw new Exception("Update failure not a file");
        }

        Path pushingP = node.getPath().toAbsolutePath();
        byte[] info = Files.readAllBytes(pushingP);


        try (FileOutputStream OS = new FileOutputStream(pushingP.toString())) {

            for (int i = 0; i < from; ++i) {
                OS.write(info[i]);
            }

            OS.write(insertedContent);

            for (int i = to; i < info.length; ++i) {
                OS.write(info[i]);
            }
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
        NodeClass res = new NodeClass(newFolderPath);
        return res;
    }

    @Override
    public Node move(Node nodeToMove, Node destinationFolder) {
        Path nodeToMovePath = nodeToMove.getPath();
        Path newNodeToMove = Path.of(destinationFolder.getPath().toString() + '/' + nodeToMovePath.getFileName()
                .toString());
        try {
            if (nodeToMove.getType() == Node.Types.FOLDER){
                FileUtils.moveDirectory(nodeToMovePath.toFile(), newNodeToMove.toFile());
            }
            else{
                Files.move(nodeToMovePath, newNodeToMove);
            }
        } catch (FileAlreadyExistsException e) {
            throw new RuntimeException("A file already exists with that name");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't move directory");
        }
        return new NodeClass(newNodeToMove);
    }
}
