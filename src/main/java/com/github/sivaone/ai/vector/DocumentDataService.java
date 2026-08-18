package com.github.sivaone.ai.vector;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
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

    /**
     * Retrieve documents from the vector store.
     * Additionally filter expressions can be used.
     * FilterExpressionBuilder b = new FilterExpressionBuilder();
     * Filter.Expression expr = b.eq("platform", "Java").build();
     * Or using fluent api builder.filterExpression("platform == 'Java'")
     *
     * @param query search query
     * @return List of documents
     */
    public List<Document> retrieveDocuments(String query) {

        return this.vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(query)
                        .topK(3)
                        .similarityThreshold(0.2)
                        .build()
        );
    }
}
