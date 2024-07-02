package ru.lenazavupach.documentzxc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.lenazavupach.documentzxc.baseresponse.BaseResponseService;
import ru.lenazavupach.documentzxc.baseresponse.ResponseWrapper;
import ru.lenazavupach.documentzxc.dto.DocumentDto;
import ru.lenazavupach.documentzxc.service.DocumentService;

import java.time.LocalDate;
import java.util.List;

@Validated
@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
@Tag(name = "Document", description = "Document operations")
public class DocumentController {
    private final DocumentService documentService;
    private final BaseResponseService baseResponseService;

    @Operation(
            summary = "Getting all documents",
            description = "Allows you to unload all documents from the database")
    @GetMapping
    public ResponseWrapper<List<DocumentDto>> findAll() {
        return baseResponseService.wrapSuccessResponse(documentService.getAll());
    }

    @Operation(
            summary = "Retrieving a document by ID",
            description = "Allows to unload a single document by ID from the database")
    @GetMapping("/document/{id}")
    public ResponseWrapper<DocumentDto> getById(@PathVariable @Min(0) Long id) {
        return baseResponseService.wrapSuccessResponse(documentService.getById(id));
    }

    @Operation(
            summary = "Search for a document by author's full name",
            description = "Allows you to find documents with a specified author")
    @GetMapping("/document/findByAuthor")
    public ResponseWrapper<List<DocumentDto>> findByAuthor(String author) {
        return baseResponseService.wrapSuccessResponse(documentService.findByAuthor(author));
    }

    @Operation(
            summary = "Search for a document by title",
            description = "Allows you to find documents with a specified title")
    @GetMapping("/document/findByTitle")
    public ResponseWrapper<List<DocumentDto>> findByTitle(String title) {
        return baseResponseService.wrapSuccessResponse(documentService.findByTitle(title));
    }

    @Operation(
            summary = "Search for a document by type",
            description = "Allows you to find documents with a specified type")
    @GetMapping("/document/findByType")
    public ResponseWrapper<List<DocumentDto>> findByType(String type) {
        return baseResponseService.wrapSuccessResponse(documentService.findByType(type));
    }

    @Operation(
            summary = "Search for a document by date",
            description = "Allows you to find documents with a specified date")
    @GetMapping("/document/findByDate")
    public ResponseWrapper<List<DocumentDto>> findByDate(LocalDate date) {
        return baseResponseService.wrapSuccessResponse(documentService.findByDate(date));
    }

    @Operation(
            summary = "create a document",
            description = "Allows you to create a new document record in the database"
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid DocumentDto documentDto) {
        documentService.create(documentDto);
    }

    @Operation(
            summary = "Update document data",
            description = "Allows you to update information about the document in the database")
    @PutMapping("/document/{id}")
    public ResponseEntity<DocumentDto> updateDocument(@PathVariable Long id, @RequestBody @Valid DocumentDto documentDto) {
        DocumentDto document = documentService.update(documentDto, id);
        return ResponseEntity.ok(document);
    }


    @Operation(
            summary = "Delete document by ID",
            description = "Allows you to delete a document by ID from the database")
    @DeleteMapping("/document/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Min(0) Long id) {
        documentService.delete(id);
    }
}
