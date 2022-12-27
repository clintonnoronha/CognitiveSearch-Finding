package com.example.demo.service;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class LuceneTextSearchUtils {
	
	Directory dir;
    Analyzer analyzer;
    IndexWriterConfig idxWriterConfig;
    IndexWriter idxWriter;
    
    public void getFSStore(String pathForIndex, boolean createNew) throws Exception{

        dir = FSDirectory.open(Paths.get(pathForIndex));
        analyzer = CustomAnalyzer.builder()
				.withTokenizer("standard")
				.addTokenFilter("stop")
				.addTokenFilter("lowercase")
				.build();
    }
    
    public List<String> searchQuery(String searchString) throws Exception {
    	
    	getFSStore("C:\\Users\\Clinton\\eclipse-workspace\\CognitiveSearchIndexing\\indexLocation", true);

        if (idxWriter !=null &&  idxWriter.isOpen()) {
            idxWriter.close();
        }

        IndexReader idxReader = DirectoryReader.open(dir);
        IndexSearcher idxSearcher = new IndexSearcher(idxReader);

        QueryParser queryParser = new QueryParser("Content", analyzer);
        Query query = queryParser.parse(searchString);

        ScoreDoc[] hits = idxSearcher.search(query, idxReader.numDocs()).scoreDocs;
        System.out.println("Number of hits :- "+hits.length);
        
        List<String> str = new ArrayList<>();
        
        for (ScoreDoc hit : hits) {
        	Document document = idxSearcher.doc(hit.doc);
        	str.add(document.get("File_attributes"));
        }
        
        return str;
    }
}