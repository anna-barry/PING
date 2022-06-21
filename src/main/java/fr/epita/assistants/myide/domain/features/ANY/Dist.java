package fr.epita.assistants.myide.domain.features.ANY;

import fr.epita.assistants.myide.domain.features.ANY.Cleanup;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Dist implements Feature {
    @Override
    public Type type() {
        return Mandatory.Features.Any.DIST;
    }

    @Override
    public ExecutionReport execute(Project project, Object... params) {
        Cleanup cleanupFeature = new Cleanup();
        cleanupFeature.execute(project, params);

        String current_directory_zip = System.getProperty("user.dir") + ".zip";

        try (ZipOutputStream archive = new ZipOutputStream(new FileOutputStream(current_directory_zip))) {
            Path tmp = project.getRootNode().getPath();
            Files.walkFileTree(tmp, new SimpleFileVisitor<Path>() {
                public FileVisitResult sub_function(Path sb) throws IOException {
                    archive.putNextEntry(new ZipEntry(project.getRootNode().getPath().getParent().relativize(sb).toString()));
                    Files.copy(sb, archive);
                    archive.closeEntry();
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            return () -> false;
        }
        return () -> true;
    }
}
