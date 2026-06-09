package com.github.sivaone.ai.vector;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentDataService {

    private final VectorStore vectorStore;

    public DocumentDataService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public void saveDocument(MyDocument myDocument) {
        Document document = new Document(myDocument.text(), myDocument.metadata());
        this.vectorStore.add(List.of(document));
    }

    public List<Document> retrieveDocuments(String query) {
        FilterExpressionBuilder b = new FilterExpressionBuilder();
        Filter.Expression expression = b.eq("platform", "Java").build();
        return this.vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(query)
                        .topK(3)
                        .similarityThreshold(0.2)
//                        .filterExpression(expression)
                        .filterExpression("platform == 'Java'")
                        .build()
        );
    }
}
