package com.example.demo.api;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.LuceneTextSearchUtils;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class DocumentSearchApi {
	
	@GetMapping(value="/search")
	public List<String> fetchDocumentsByQuery(@Valid @RequestParam("query") String query, @RequestParam("type") String type) {
		System.out.println("Query is : " + query);
		System.out.println("Search as : "+" "+ type);
		LuceneTextSearchUtils utils = new LuceneTextSearchUtils();
		List<String> docsList = null;
		try {
			docsList = new ArrayList<>(utils.searchQuery(query));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return docsList;
	}
}