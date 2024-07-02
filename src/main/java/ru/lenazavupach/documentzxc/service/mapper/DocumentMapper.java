package ru.lenazavupach.documentzxc.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import ru.lenazavupach.documentzxc.dto.DocumentDto;
import ru.lenazavupach.documentzxc.entity.Company;
import ru.lenazavupach.documentzxc.entity.Document;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DocumentMapper {

    @Mapping(target = "companyId", source = "company", qualifiedByName = "mapCompanyToCompanyId")
    DocumentDto toDto(Document document);

    Document toEntity(DocumentDto documentDto);

    List<DocumentDto> toListDto(List<Document> documents);

    List<Document> toEntities(List<DocumentDto> documentDtos);

    void merge(@MappingTarget Document target, Document source);

    @Named("mapCompanyToCompanyId")
    static Long mapCompanyToCompanyId(Company company) {
        return company != null ? company.getId() : null;
    }

}
