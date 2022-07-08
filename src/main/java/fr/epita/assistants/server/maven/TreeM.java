package fr.epita.assistants.server.maven;

import com.sun.net.httpserver.HttpExchange;
import fr.epita.assistants.MyIde;
import fr.epita.assistants.server.MyHttpException;
import fr.epita.assistants.server.MyHttpHandler;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;

import java.util.Optional;

public class TreeM extends MyHttpHandler {

    public TreeM() {
        super("GET", "feature/maven/tree");
    }
    /**
     * mvn dependency:tree
     */

    @Override
    public Object handle(HttpExchange exchange) throws MyHttpException {
        Optional<Feature> f = MyIde.p.getFeature(Mandatory.Features.Maven.TEST);
        if (f.isEmpty())
            throw new MyHttpException(400, "Feature not supported.");
        return f.get().execute(MyIde.p);
    }
}
