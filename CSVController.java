package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.csv.CsvParser;
import com.example.demo.csv.MarginSearchRequest;
import com.example.demo.models.Margin;
import com.example.demo.repositories.MarginRepository;

@RestController
public class CSVController {
	
	@Autowired
	MarginRepository marginRepository;
	
	@PostMapping("/upload")
	public ResponseEntity<?> uploadMarginFile(@RequestParam("file") MultipartFile file) {
		if (!file.getOriginalFilename().endsWith(".csv")) {
            return ResponseEntity.badRequest().body("Invalid file format. Expected CSV file.");
        }
		try {
	        // Step 1: Parse the CSV file and validate each row against the specified field requirements
	        List<Margin> margins = CsvParser.parseCsv(file.getInputStream());
	        /*for (Margin margin : margins) {
	            if (!margin.isValid()) {
	                // If a row fails validation, return a response indicating the error and stop processing the file
	                return ResponseEntity.badRequest().body("Invalid row: " + margin.toString());
	            }
	        }*/

	        // Step 2: Delete existing margin data from the database
	        marginRepository.deleteAll();

	        // Step 3: Insert the new margin data into the database in the correct order
	        for (int i = 0; i < margins.size(); i++) {
	        	Margin margin = margins.get(i);
	        	if (margin.isValid()) {
	        		margin.setMarginOrder(i + 1); // Set the margin order based on the row index
	        		marginRepository.save(margin);
	        	}
	        }

	        // Return a success response
	        return ResponseEntity.ok("File uploaded successfully");

	    } catch (IOException e) {
	        // If there was an error reading the file, return a response indicating the error
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reading file: " + e.getMessage());
	    }
	}
	
	@GetMapping("/download")
	public ResponseEntity<Resource> downloadMarginData() {
	    // Step 1: Retrieve all margin data from the database
	    List<Margin> margins = marginRepository.findAll();

	    // Step 2: Convert the margin data to a CSV file
	    String csvData = CsvParser.convertToCsv(margins);

	    // Step 3: Create a Resource object from the CSV data
	    ByteArrayResource resource = new ByteArrayResource(csvData.getBytes());

	    // Step 4: Set the content type and attachment filename headers for the response
	    HttpHeaders headers = new HttpHeaders();
	    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=margins.csv");
	    headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");

	    // Step 5: Return a ResponseEntity with the CSV file as the response body
	    return ResponseEntity.ok().headers(headers).body(resource);
	}
	
	@PostMapping("/find")
	public ResponseEntity<?> findMargin(@RequestBody MarginSearchRequest searchRequest) {
	    try {
	        // Get the margin matching the search request from the repository
	        Margin margin = marginRepository.findFirstBySearchRequest(searchRequest.getInstruction(),
	        		searchRequest.getBaseCcy(),searchRequest.getTermCcy(),
	        		searchRequest.getTraderTier(),searchRequest.getAmount());

	        if (margin == null) {
	            // If there is no matching margin, return a response indicating that no margin was found
	            return ResponseEntity.notFound().build();
	        } else {
	            // If there is a matching margin, return it in the response
	            return ResponseEntity.ok(margin);
	        }
	    } catch (Exception e) {
	        // If there was an error processing the request, return a response indicating the error
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request: " + e.getMessage());
	    }
	}

}

