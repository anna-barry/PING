package fr.epita.assistants.myide.domain;

import fr.epita.assistants.myide.domain.entity.Aspect;
import fr.epita.assistants.myide.domain.entity.Feature;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AspectClass implements Aspect {
    @NotNull private final Type type;

    public AspectClass(Type type, List<Feature> featureList) {
        this.type = type;
    }

    @Override
    public Type getType() {
        return type;
    }
}
