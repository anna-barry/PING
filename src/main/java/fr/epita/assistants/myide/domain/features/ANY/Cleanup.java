package fr.epita.assistants.myide.domain.features.ANY;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.Project;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class Cleanup implements Feature {
        @Override
        public Type type() {
            return Mandatory.Features.Any.CLEANUP;
        }

        @Override
        public ExecutionReport execute(Project project, Object... params) {

            Optional<Node> empty = project.getRootNode().getChildren().stream()
                    .filter(node -> node.getPath().getFileName().toString().equals(".myideignore"))
                    .findAny();
            if (empty.isEmpty())
                return () -> false;
            try {
                sub(project.getRootNode(),Files.readAllLines(empty.get().getPath()));
            } catch (IOException e) {
                return () -> false;
            }
            return () -> true;
    }

    private void sub(Node node, List<String> l) {
        if (node.getType() == Node.Types.FILE)
            return;

        for (int i = node.getChildren().size() - 1; i >= 0; i--) {
            Node child = node.getChildren().get(i);
            if (l.stream().anyMatch(reg -> child.getPath().getFileName().toString().matches(reg))) {
                node.getChildren().remove(child);
                File[] files = child.getPath().toFile().listFiles();
                if (files != null) {
                    for (final File file : files)
                        subsub(file);
                }
                node.getPath().toFile().delete();
            } else {
                sub(child, l);
            }
        }
    }

    private void subsub(File f)
    {
        File[] files = f.listFiles();
        if (files != null) {
            for (final File file : files)
                subsub(file);
        }
        f.delete();
    }
}
