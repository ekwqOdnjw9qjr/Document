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
import ru.lenazavupach.documentzxc.dto.CompanyDto;
import ru.lenazavupach.documentzxc.service.CompanyService;

import java.util.List;

@Validated
@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
@Tag(name = "Компания", description = "Операции над художниками")
public class CompanyController {
    private final CompanyService companyService;
    private final BaseResponseService baseResponseService;

    @Operation(
            summary = "Получение всех художников",
            description = "Позволяет выгрузить всех художников из БД"
    )
    @GetMapping
    public ResponseWrapper<List<CompanyDto>> findAll() {
        return baseResponseService.wrapSuccessResponse(companyService.getAll());
    }

    @Operation(
            summary = "Получение художника по ID",
            description = "Позволяет выгрузить одного художника по ID из БД"
    )
    @GetMapping("/company/{id}")
    public ResponseWrapper<CompanyDto> getById(@PathVariable @Min(0) Long id) {
        return baseResponseService.wrapSuccessResponse(companyService.getById(id));
    }

    @Operation(
            summary = "Создать художника",
            description = "Позволяет создать новую запись о художнике в БД"
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid CompanyDto companyDto) {
        companyService.create(companyDto);
    }


    @Operation(
            summary = "Обновить данные о художнике",
            description = "Позволяет обновить информацию о художнике в БД"
    )
    @PutMapping("/{id}")
    public CompanyDto update(@PathVariable Long id, @RequestBody @Valid CompanyDto companyDto) {
        return companyService.update(companyDto, id);
    }

    @Operation(
            summary = "Удалить художника по ID",
            description = "Позволяет удалить художника по ID из БД"
    )
    @DeleteMapping("/company/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Min(0) Long id) {
        companyService.delete(id);
    }
}
