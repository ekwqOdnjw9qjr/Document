package ru.lenazavupach.documentzxc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.lenazavupach.documentzxc.baseresponse.BaseResponseService;
import ru.lenazavupach.documentzxc.baseresponse.ResponseWrapper;
import ru.lenazavupach.documentzxc.dto.DocumentDto;
import ru.lenazavupach.documentzxc.service.DocumentService;

import java.util.List;

@Validated
@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
@Tag(name = "ДОкументы", description = "Операции над картинами")
public class DocumentController {
    private final DocumentService documentService;
    private final BaseResponseService baseResponseService;

    @Operation(
            summary = "Получение всех картин",
            description = "Позволяет выгрузить все картины из БД")
    @GetMapping
    public ResponseWrapper<List<DocumentDto>> findAll() {
        return baseResponseService.wrapSuccessResponse(documentService.getAll());
    }

    @Operation(
            summary = "Получение картины по ID",
            description = "Позволяет выгрузить одну картину по ID из БД")
    @GetMapping("/document/{id}")
    public ResponseWrapper<DocumentDto> getById(@PathVariable @Min(0) Long id) {
        return baseResponseService.wrapSuccessResponse(documentService.getById(id));
    }


    @Operation(
            summary = "Создать картину",
            description = "Позволяет создать новую запись о картине в БД"
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid DocumentDto documentDto) {
        documentService.create(documentDto);
    }

    @Operation(
            summary = "Обновить данные о картине ",
            description = "Позволяет обновить информацию о картине в БД")
    @PutMapping("/document/")
    public DocumentDto updateDocument(@PathVariable Long id, @RequestBody @Valid DocumentDto documentDto) {
        return documentService.update(documentDto, id);
    }

    @Operation(
            summary = "Удалить картину по ID ",
            description = "Позволяет удалить картину по ID из БД")
    @DeleteMapping("/document/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Min(0) Long id) {
        documentService.delete(id);
    }
}
