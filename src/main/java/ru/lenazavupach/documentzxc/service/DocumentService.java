package ru.lenazavupach.documentzxc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lenazavupach.documentzxc.dto.DocumentDto;
import ru.lenazavupach.documentzxc.entity.Company;
import ru.lenazavupach.documentzxc.entity.Document;
import ru.lenazavupach.documentzxc.exception.CurrentException;
import ru.lenazavupach.documentzxc.exception.ErrorType;
import ru.lenazavupach.documentzxc.repository.CompanyRepository;
import ru.lenazavupach.documentzxc.repository.DocumentRepository;
import ru.lenazavupach.documentzxc.service.mapper.DocumentMapper;

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

    public List<DocumentDto> getAll() {
        List<Document> documents = documentRepository.findAll();
        return documentMapper.toListDto(documents);
    }


    public void create(DocumentDto documentDto) {

        Document document = documentMapper.toEntity(documentDto);
        document.setCompany(companyRepository.findByName(documentDto.getCompanyId()));
        documentRepository.save(document);
    }

    public DocumentDto update(DocumentDto documentDto, Long id) {
        Document oldDocument = documentRepository.findById(id)
                .orElseThrow(() -> new CurrentException(ErrorType.NOT_FOUND, "Document with id: " + id + " not found"));
        Document newDocument = documentMapper.toEntity(documentDto);
        documentMapper.merge(oldDocument, newDocument);
        Document savedDocument = documentRepository.save(oldDocument);
        return documentMapper.toDto(savedDocument);
    }

    public void delete(Long id) {
        documentRepository.deleteById(id);
    }

}
