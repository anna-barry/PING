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
import java.util.stream.Stream;

public class Cleanup implements Feature {
        @Override
        public Type type() {
            return Mandatory.Features.Any.CLEANUP;
        }

        @Override
        public ExecutionReport execute(Project project, Object... params) {
            class Result implements ExecutionReport {

                public Result(boolean res) {
                    this.Result_ = res;
                }

                @Override
                public boolean isSuccess() {
                    return Result_;
                }

                public void setSuccess(boolean res) {
                    this.Result_ = res;
                }
                private boolean Result_;
            }
            Result report = new Result(false);

            try (Stream<String> stream = Files.lines(Paths.get(project.getRootNode().getPath().toAbsolutePath() + File.separator+ ".myideignore"))) {
                stream.forEach(file -> {
                    File f = new File(project.getRootNode().getPath().toAbsolutePath() + File.separator + file);

                    if (!f.isDirectory()) {
                        f.delete();
                    } else {
                        subsub(f);
                    }
                });
            } catch (IOException e) {
                return report;
            }
            report.setSuccess(true);
            return report;
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
            for (File f2 : files)
                subsub(f2);
        }
        f.delete();
    }
}
