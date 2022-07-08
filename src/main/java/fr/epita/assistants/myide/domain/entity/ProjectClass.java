package fr.epita.assistants.myide.domain.entity;

import fr.epita.assistants.myide.domain.entity.Aspect;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.features.ANY.*;
import fr.epita.assistants.myide.domain.features.MAVEN.Package;
import fr.epita.assistants.myide.domain.features.git.*;
import fr.epita.assistants.myide.domain.features.MAVEN.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ProjectClass implements Project {
    @NotNull private final Node rootNode;
    @NotNull private final Set<Aspect> aspects;
    private Set<Feature> features_;

    @Override
    public Optional<Feature> getFeature(Feature.Type featureType) {

        if (Mandatory.Features.Any.CLEANUP.equals(featureType))
            return Optional.of(new Cleanup());
        else if (Mandatory.Features.Any.DIST.equals(featureType))
            return Optional.of(new Dist());
        else if (Mandatory.Features.Any.SEARCH.equals(featureType))
            return Optional.of(new Search());

        return features_.stream().filter(feature -> feature.type() == featureType).findFirst();
    }

    public ProjectClass(Node rootNode, Set<Aspect> aspects) {
        this.rootNode = rootNode;
        this.aspects = aspects;
        features_ = new HashSet<Feature>();
        try {
            for (Aspect aspect : aspects) {
                features_.addAll(aspect.getFeatureList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public Node getRootNode() {
        return rootNode;
    }

    @Override
    public Set<Aspect> getAspects() {
        return aspects;
    }
}
