package fr.epita.assistants.myide.domain.features;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class Search implements Feature {
    @Override
    public Type type() {
        return Mandatory.Features.Any.SEARCH;
    }

    @Override
    public ExecutionReport execute(Project project, Object... params) {
        try {
            String current_directory = System.getProperty("user.dir");
            Directory directory = FSDirectory.open(Path.of(current_directory));
            IndexWriter indexWriter = new IndexWriter(directory, new IndexWriterConfig(new StandardAnalyzer()));

            IndexReader reader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(reader);

            //QueryParser parser = new QueryParser(Version.LUCENE_9_2_0, "contents", indexWriter.getAnalyzer());
            QueryParser parser = new QueryParser("", indexWriter.getAnalyzer());
            Query query2 = parser.Query(params[0].toString());
            TopDocs topDocs = searcher.search(query2, 1000);

            ScoreDoc[] hits = topDocs.scoreDocs;
            for (int i = 0; i < hits.length; i++) {
                int docId = hits[i].doc;
                Document d = searcher.doc(docId);
                System.out.println(d.get("filename"));
            }

            System.out.println("Found " + hits.length);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
