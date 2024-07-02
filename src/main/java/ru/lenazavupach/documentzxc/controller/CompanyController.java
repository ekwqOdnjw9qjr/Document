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
@Tag(name = "Company", description = "Operations on companies")
public class CompanyController {
    private final CompanyService companyService;
    private final BaseResponseService baseResponseService;

    @Operation(
            summary = "Getting all the companies",
            description = "Allows you to unload all companies from the database"
    )
    @GetMapping
    public ResponseWrapper<List<CompanyDto>> findAll() {
        return baseResponseService.wrapSuccessResponse(companyService.getAll());
    }

    @Operation(
            summary = "Getting a company by ID",
            description = "Allows you to upload a company by ID from the database"
    )
    @GetMapping("/company/{id}")
    public ResponseWrapper<CompanyDto> getById(@PathVariable @Min(0) Long id) {
        return baseResponseService.wrapSuccessResponse(companyService.getById(id));
    }

    @Operation(
            summary = "Search for a company by name",
            description = "Allows you to find companies with the specified name"
    )
    @GetMapping("/company/findByName")
    public ResponseWrapper<List<CompanyDto>> findByName(String name) {
        return baseResponseService.wrapSuccessResponse(companyService.findCompanyByName(name));
    }
    @Operation(
            summary = "Search for a company by address",
            description = "Allows you to find companies with the specified address"
    )
    @GetMapping("/company/findByAddress")
    public ResponseWrapper<List<CompanyDto>> findByAddress(String address) {
        return baseResponseService.wrapSuccessResponse(companyService.findCompanyByAddress(address));
    }

    @Operation(
            summary = "Search for a company by email address",
            description = "Allows you to find companies with the specified email address"
    )
    @GetMapping("/company/findByEmail")
    public ResponseWrapper<List<CompanyDto>> findByEmail(String email) {
        return baseResponseService.wrapSuccessResponse(companyService.findCompanyByEmail(email));
    }

    @Operation(
            summary = "Search for a company by phone number",
            description = "Allows you to find companies with the specified phone number"
    )
    @GetMapping("/company/findByPhoneForCalls")
    public ResponseWrapper<List<CompanyDto>> findByPhoneForCalls(Long phoneForCalls) {
        return baseResponseService.wrapSuccessResponse(companyService.findCompanyByPhoneForCalls(phoneForCalls));
    }

    @Operation(
            summary = "Search for a company by INN",
            description = "Allows you to find companies with the specified INN"
    )
    @GetMapping("/company/findByInn")
    public ResponseWrapper<List<CompanyDto>> findByInn(Long inn) {
        return baseResponseService.wrapSuccessResponse(companyService.findCompanyByInn(inn));
    }

    @Operation(
            summary = "Create a company",
            description = "Allows you to create a new company record in the database"
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid CompanyDto companyDto) {
        companyService.create(companyDto);
    }


    @Operation(
            summary = "Update company information",
            description = "Allows you to update company information in the database"
    )
    @PutMapping("/{id}")
    public CompanyDto update(@PathVariable Long id, @RequestBody @Valid CompanyDto companyDto) {
        return companyService.update(companyDto, id);
    }

    @Operation(
            summary = "Delete a company by ID",
            description = "Allows you to delete a company by ID from the database"
    )
    @DeleteMapping("/company/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Min(0) Long id) {
        companyService.delete(id);
    }
}
