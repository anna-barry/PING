package fr.epita.assistants.myide.domain.features;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Project;

public class Cleanup implements Feature {
    public Cleanup(Type type) {
        super(type);
    }

    @Override
    public ExecutionReport execute(Project project, Object... params) {
        return null;
    }
}
