package ru.lenazavupach.documentzxc.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import ru.lenazavupach.documentzxc.dto.CompanyDto;
import ru.lenazavupach.documentzxc.entity.Company;
import ru.lenazavupach.documentzxc.entity.Document;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompanyMapper {

    @Mapping(target = "documentsIds", source = "documents", qualifiedByName = "mapDocumentsToIds")
    CompanyDto toDto(Company company);

    Company toEntity(CompanyDto companyDto);

    List<CompanyDto> toListDto(List<Company> companies);

    List<Company> toEntities(List<CompanyDto> companyDtos);

    void merge(@MappingTarget Company target, Company source);

    @Named("mapDocumentsToIds")
    default List<Long> mapDocumentsToIds(List<Document> documents) {
        if (documents == null) {
            return null;
        }
        return documents.stream()
                .map(Document::getId)
                .collect(Collectors.toList());
    }
}
