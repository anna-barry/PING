package fr.epita.assistants.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import fr.epita.assistants.MyIde;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.nio.file.Path;

public class Timing extends MyHttpHandler {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    class ProjectLoadDto {
        private String path;
    }

    public Timing() {
        super("POST", "/timing");
    }

    @Override
    public Object handle(HttpExchange exchange) throws MyHttpException {
        System.out.println("handle for timing java");
        String body = Utils.read(exchange.getRequestBody());
        ProjectLoadDto dto = Utils.deserialize(body, ProjectLoadDto.class);
        System.out.println(body);
        if (dto == null) {
            // Bad request
            throw new MyHttpException(400);
        }


        //System.out.println(new Gson().toJson(MyIde.p.getRootNode().toObject()));

        //JsonObject result = new JsonObject().add("nodes", MyIde.p.getRootNode().toObject());

       // result.add("aspects", MyIde.p.getAspects()).add("features", MyIde.project.getFeatures());


        return null;
    }
}

// Load d-un projet POST => chemin du dossier, renvoie les infos sur le projet (features, aspects, nodes deja fait,
// Recuperation d'un fichier du projet POST => chemin du dossier, renvoie les contenu du fichier et son chemin absolu.
// Definition du contenu d'un fichier du projet POST => chemin du dossier, contenu du fichier, essaye de set le contenu et ca renvoie le contenu.