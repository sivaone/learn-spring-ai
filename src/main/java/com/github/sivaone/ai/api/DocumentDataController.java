package com.github.sivaone.ai.api;

import com.github.sivaone.ai.vector.DocumentDataService;
import com.github.sivaone.ai.vector.MyDocument;
import org.springframework.ai.document.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentDataController {

    private final DocumentDataService documentDataService;

    public DocumentDataController(DocumentDataService documentDataService) {
        this.documentDataService = documentDataService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveDocument(@RequestBody MyDocument document) {
        this.documentDataService.saveDocument(document);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Document>> getDocuments(@RequestParam(required = false) String query) {
        List<Document> documents = this.documentDataService.retrieveDocuments(query);
        return ResponseEntity.ok(documents);
    }
}
