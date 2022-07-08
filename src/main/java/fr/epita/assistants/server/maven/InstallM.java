package fr.epita.assistants.server.maven;

import com.sun.net.httpserver.HttpExchange;
import fr.epita.assistants.MyIde;
import fr.epita.assistants.server.MyHttpException;
import fr.epita.assistants.server.MyHttpHandler;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;

import java.util.Optional;

public class InstallM extends MyHttpHandler {

    public InstallM() {
        super("GET", "feature/maven/install");
    }
    /**
     * mvn install
     */

    @Override
    public Object handle(HttpExchange exchange) throws MyHttpException {
        Optional<Feature> f = MyIde.p.getFeature(Mandatory.Features.Maven.INSTALL);
        if (f.isEmpty())
            throw new MyHttpException(400, "Feature not supported.");
        return f.get().execute(MyIde.p);
    }
}
