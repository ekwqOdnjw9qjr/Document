package ru.lenazavupach.documentzxc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lenazavupach.documentzxc.dto.DocumentDto;
import ru.lenazavupach.documentzxc.entity.Company;
import ru.lenazavupach.documentzxc.entity.Document;
import ru.lenazavupach.documentzxc.exception.CurrentException;
import ru.lenazavupach.documentzxc.exception.ErrorType;
import ru.lenazavupach.documentzxc.repository.CompanyRepository;
import ru.lenazavupach.documentzxc.repository.DocumentRepository;
import ru.lenazavupach.documentzxc.service.mapper.DocumentMapper;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;
    private final CompanyRepository companyRepository;

    public DocumentDto getById(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new CurrentException(ErrorType.NOT_FOUND,"Document with id: " + id + " not found"));
        return documentMapper.toDto(document);
    }

    public List<DocumentDto> findByAuthor(String author) {
        List<Document> documentList = documentRepository.findDocumentByAuthor(author);
        return documentMapper.toListDto(documentList);
    }

    public List<DocumentDto> findByTitle(String title) {
        List<Document> documentList = documentRepository.findDocumentByTitle(title);
        return documentMapper.toListDto(documentList);
    }

    public List<DocumentDto> findByType(String type) {
        List<Document> documentList = documentRepository.findDocumentByType(type);
        return documentMapper.toListDto(documentList);
    }

    public List<DocumentDto> findByDate(LocalDate date) {
        List<Document> documentList = documentRepository.findDocumentByDate(date);
        return documentMapper.toListDto(documentList);
    }

    public List<DocumentDto> getAll() {
        List<Document> documentList = documentRepository.findAll();
        return documentMapper.toListDto(documentList);
    }


    @Transactional
    public void create(DocumentDto documentDto) {
        // Проверяем, существует ли компания с указанным companyId
        Company company = companyRepository.findById(documentDto.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));

        Document document = documentMapper.toEntity(documentDto);
        document.setCompany(company); // Устанавливаем связь с компанией

        documentRepository.save(document);
    }

    public DocumentDto update(DocumentDto documentDto, Long id) {
        Document oldDocument = documentRepository.findById(id)
                .orElseThrow(() -> new CurrentException(ErrorType.NOT_FOUND, "Document with id: " + id + " not found"));
        Document newDocument = documentMapper.toEntity(documentDto);
        documentMapper.merge(oldDocument, newDocument);
//            Company company = companyRepository.findByName((documentDto.getCompanyId()));
           // oldDocument.setCompany(company);
        Document savedDocument = documentRepository.save(oldDocument);
        return documentMapper.toDto(savedDocument);
    }

    public void delete(Long id) {
        documentRepository.deleteById(id);
    }

}
