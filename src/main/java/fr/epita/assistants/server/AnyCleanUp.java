package fr.epita.assistants.server;

import com.sun.net.httpserver.HttpExchange;
import fr.epita.assistants.MyIde;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;

import java.util.Optional;

public class AnyCleanUp extends MyHttpHandler {

    public AnyCleanUp() {
        super("GET", "feature/any/clean");
    }

    /**
     * Remove all trash files.
     * Trash files are listed, line by line,
     * in a ".myideignore" file at the root of the project.
     */

    @Override
    public Object handle(HttpExchange exchange) throws MyHttpException {
        Optional<Feature> f = MyIde.p.getFeature(Mandatory.Features.Any.CLEANUP);
        if (f.isEmpty())
            throw new MyHttpException(400, "Feature not supported.");
        return f.get().execute(MyIde.p);
    }
}
