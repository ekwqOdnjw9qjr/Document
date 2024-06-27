package ru.lenazavupach.documentzxc.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.lenazavupach.documentzxc.dto.CompanyDto;
import ru.lenazavupach.documentzxc.entity.Company;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompanyMapper {

    CompanyDto toDto(Company company);

    Company toEntity(CompanyDto companyDto);

    List<CompanyDto> toListDto(List<Company> companies);

    List<Company> toEntities(List<CompanyDto> companyDtos);

    void merge(@MappingTarget Company target, Company source);
}
