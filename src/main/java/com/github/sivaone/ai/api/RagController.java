package com.github.sivaone.ai.api;

import com.github.sivaone.ai.rag.RagService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rag")
public class RagController {

    private final RagService ragService;

    public RagController(RagService ragService) {
        this.ragService = ragService;
    }

    @GetMapping("/generate")
    public String generate(@RequestParam String prompt) {
        return this.ragService.generateResponse(prompt);
    }
}
