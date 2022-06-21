package fr.epita.assistants.myide.domain.aspects;

import fr.epita.assistants.myide.domain.entity.Aspect;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.features.ANY.Cleanup;
import fr.epita.assistants.myide.domain.features.ANY.Dist;
import fr.epita.assistants.myide.domain.features.ANY.Search;
import fr.epita.assistants.myide.domain.features.MAVEN.Compile;

import java.util.List;

public class AnyAspect implements Aspect{
    @Override
    public Type getType() {
        return Mandatory.Aspects.ANY;
    }

    @Override
    public List<Feature> getFeatureList() {
        return List.of(new Cleanup(), new Compile(), new Dist(), new Search());
    }
}
