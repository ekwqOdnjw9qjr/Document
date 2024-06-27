package ru.lenazavupach.documentzxc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lenazavupach.documentzxc.dto.CompanyDto;
import ru.lenazavupach.documentzxc.entity.Company;

public interface CompanyRepository extends JpaRepository<Company,Long> {

    Company findByName(String name);
}
