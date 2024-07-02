package ru.lenazavupach.documentzxc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lenazavupach.documentzxc.dto.CompanyDto;
import ru.lenazavupach.documentzxc.entity.Company;
import ru.lenazavupach.documentzxc.entity.Document;
import ru.lenazavupach.documentzxc.exception.CurrentException;
import ru.lenazavupach.documentzxc.exception.ErrorType;
import ru.lenazavupach.documentzxc.repository.CompanyRepository;
import ru.lenazavupach.documentzxc.repository.DocumentRepository;
import ru.lenazavupach.documentzxc.service.mapper.CompanyMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final DocumentRepository documentRepository;

    private static final Logger logger = Logger.getLogger(CompanyService.class.getName());


    public List<CompanyDto> getAll() {
        List<Company> companyList = companyRepository.findAll();
        return companyMapper.toListDto(companyList);
    }

    public CompanyDto getById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new CurrentException(ErrorType.NOT_FOUND,"Company with id: " + id + " not found"));
        return companyMapper.toDto(company);
    }

    public List<CompanyDto> findCompanyByName(String name) {
        List<Company> companyList = companyRepository.findCompaniesByName(name);
        return companyMapper.toListDto(companyList);
    }

    public List<CompanyDto> findCompanyByAddress(String address) {
        List<Company> companyList = companyRepository.findCompaniesByAddress(address);
        return companyMapper.toListDto(companyList);
    }

    public List<CompanyDto> findCompanyByEmail(String email) {
        List<Company> companyList = companyRepository.findCompaniesByEmail(email);
        return companyMapper.toListDto(companyList);
    }

    public List<CompanyDto> findCompanyByInn(Long inn) {
        List<Company> companyList = companyRepository.findCompaniesByInn(inn);
        return companyMapper.toListDto(companyList);
    }

    public List<CompanyDto> findCompanyByPhoneForCalls(Long phoneForCalls) {
        List<Company> companyList = companyRepository.findCompaniesByPhoneForCalls(phoneForCalls);
        return companyMapper.toListDto(companyList);
    }

    @Transactional
    public void create(CompanyDto companyDto) {
        Company company = companyMapper.toEntity(companyDto);

        // Если есть идентификаторы документов, связываем компанию с этими документами
        if (companyDto.getDocumentsIds() != null && !companyDto.getDocumentsIds().isEmpty()) {
            List<Document> documents = documentRepository.findAllById(companyDto.getDocumentsIds());
            company.setDocuments(new ArrayList<>(documents));
        }

        companyRepository.save(company);
    }

    public CompanyDto update(CompanyDto companyDto, Long id) {
        Company oldCompany = companyRepository.findById(id)
                .orElseThrow(() -> new CurrentException(ErrorType.NOT_FOUND,"Company with id: " + id + " not found"));
        if (companyDto.getDocumentsIds() == null) {
            companyDto.setDocumentsIds(new ArrayList<>());
        }
        Company newCompany = companyMapper.toEntity(companyDto);
        companyMapper.merge(oldCompany, newCompany);
        Company savedCompany = companyRepository.save(oldCompany);
        return companyMapper.toDto(savedCompany);
    }

    public void delete(Long id) {
        companyRepository.deleteById(id);

    }

}
