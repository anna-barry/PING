package fr.epita.assistants.myide.domain.features.ANY;

import fr.epita.assistants.myide.domain.features.ANY.Cleanup;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

import java.io.File;
import java.io.FileInputStream;
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

    public class Result implements ExecutionReport {

        public Result(boolean Result) {this.Result_ = Result;}

        @Override
        public boolean isSuccess() {
            return Result_;
        }

        final boolean Result_;
    }

    @Override
    public ExecutionReport execute(Project project, Object... params) {
        Cleanup cleanupFeature = new Cleanup();
        cleanupFeature.execute(project, params);
        if (project.getRootNode().isFile()) {
            try {
                var OS = new FileOutputStream(project.getRootNode().getPath().toString() + ".zip");

                var ozip = new ZipOutputStream(OS);
                var file = new File(project.getRootNode().getPath().toString());

                var inputFS = new FileInputStream(file);
                var inputZ = new ZipEntry(file.getName());

                ozip.putNextEntry(inputZ);

                byte[] bytes = new byte[1024];

                for (int length = inputFS.read(bytes); length >= 0; length = inputFS.read(bytes))
                    ozip.write(bytes, 0, length);

                ozip.close();
                inputFS.close();
                OS.close();
            } catch (IOException e) {
                return new Result(false);
            }
        } else {
            try {
                FileOutputStream OS = new FileOutputStream(project.getRootNode().getPath().toString() + ".zip");
                ZipOutputStream zipOut = new ZipOutputStream(OS);
                File file = new File(project.getRootNode().getPath().toString());
                sub(file, file.getName(), zipOut);
                zipOut.close();
                OS.close();
            } catch (IOException e) {
                return new Result(false);
            }
        }
        return new Result(true);
    }

    public void sub(File file, String folder, ZipOutputStream ZO) throws IOException {
        if (file.isDirectory()) {
            if (!folder.endsWith("/")) {
                folder += "/";
            }
            ZO.putNextEntry(new ZipEntry(folder));
            ZO.closeEntry();
            File[] files = file.listFiles();
            for (var child : files) {
                sub( child, folder + child.getName(), ZO);
            }
        } else {
            var IS = new FileInputStream(file);
            var zip = new ZipEntry(folder);
            ZO.putNextEntry(zip);

            byte[] bytes = new byte[1024];

            for (int length = IS.read(bytes); length >= 0; length = IS.read(bytes))
                ZO.write(bytes, 0, length);
            IS.close();
        }
    }
}
