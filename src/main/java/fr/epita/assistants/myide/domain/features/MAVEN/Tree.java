package fr.epita.assistants.myide.domain.features.MAVEN;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.StreamHandler;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Tree implements Feature {
    public Tree()
    {

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

        try {
            var command = "dependency:tree";
            var exe = sub(command, project.getRootNode().getPath().toString()
                    , params);
            if (exe != 0) {
                return report;
            }
        } catch (IOException | InterruptedException e) {
            return report;
        }


        report.setSuccess(true);
        return report;
    }

    public static int sub(String command, String project, Object... args) throws IOException, InterruptedException {

        var commands = new ArrayList<String>();
        String str = (String) Arrays.stream(args).toList().get(0);

        commands.add("mvn");
        commands.add(command);
        int i = 0;
        for (var e : str.split(" ")) {

            if(i == 0 && e.toString().startsWith(":")) {
                commands.set(1, commands.get(1) + e.toString());
            }
            else
            if(e.toString() != "" && e.toString() != null && e != null && !e.toString().isBlank()) {
                commands.add(e.toString());
            }
            i +=1;
        }

        ProcessBuilder pb = new ProcessBuilder(commands);
        System.out.println(project);
        pb.directory(new File(project));

        if(System.getProperty("os.name").startsWith("Windows")) {
            commands.add(0, "/c");
            commands.add(0, "cmd");

        }

        System.out.println(commands);
        Process pushingP = pb.start();
        new help4stream(pushingP.getInputStream()).run(false);
        new help4stream(pushingP.getErrorStream()).run(true);
        return pushingP.waitFor();

    }

    @Override
    public Type type() {
        return Mandatory.Features.Maven.TREE;
    }
}
