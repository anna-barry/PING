package fr.epita.assistants.myide.domain.aspects;

import fr.epita.assistants.myide.domain.entity.Aspect;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.features.ANY.Cleanup;
import fr.epita.assistants.myide.domain.features.MAVEN.*;
import fr.epita.assistants.myide.domain.features.MAVEN.Package;

import java.util.List;

public class MavenAspect implements Aspect {
    @Override
    public Type getType() {
        return Mandatory.Aspects.MAVEN;
    }

    @Override
    public List<Feature> getFeatureList() {
        return List.of(new Clean(), new Compile(), new Exec(), new Install(), new Package(), new Test(), new Tree());
    }
}
