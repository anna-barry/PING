package fr.epita.assistants.myide.domain.features;
import fr.epita.assistants.MyIde;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;

import java.util.Optional;

import static spark.Spark.*;

public class testLink {
    public void runtest() {
        Optional<Feature> feature = MyIde.p.getFeature(Mandatory.Features.Maven.CLEAN);

       // get("/hello", (req, res) -> feature.get().execute(MyIde.p));
        get("/hello", (req, res) -> {System.out.println("hi"); return feature.get().execute(MyIde.p);});
    }
}
