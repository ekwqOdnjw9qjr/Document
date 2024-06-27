package ru.lenazavupach.documentzxc.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.lenazavupach.documentzxc.dto.DocumentDto;
import ru.lenazavupach.documentzxc.entity.Document;

import java.util.List;

@Mapper(componentModel = "spring")
public interface  DocumentMapper {

    DocumentDto toDto(Document document);

    Document toEntity(DocumentDto documentDto);

    List<DocumentDto> toListDto(List<Document> documents);

    List<Document> toEntities(List<DocumentDto> documentDtos);

    void merge(@MappingTarget Document target, Document source);
}