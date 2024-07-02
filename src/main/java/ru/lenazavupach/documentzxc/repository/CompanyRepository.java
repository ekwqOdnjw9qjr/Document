package ru.lenazavupach.documentzxc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.lenazavupach.documentzxc.dto.CompanyDto;
import ru.lenazavupach.documentzxc.entity.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company,Long> {

//    Company findByName(String name);

    @Query("SELECT c FROM Company c WHERE c.address = :address AND c.email = :email " +
            "AND c.phoneForCalls = :phoneForCalls AND c.inn = :inn")
    Optional<Company> findByUniqueAttributes(@Param("address") String address,
                                             @Param("email") String email,
                                             @Param("phoneForCalls") Long phoneForCalls,
                                             @Param("inn") Long inn);

    @Query("SELECT c FROM Company c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')) ")
    List<Company> findCompaniesByName(@Param("name") String name);

    @Query("SELECT c FROM Company c WHERE LOWER(c.address) LIKE LOWER(CONCAT('%', :address, '%'))")
    List<Company> findCompaniesByAddress(@Param("address") String address);

    @Query("SELECT c FROM Company c WHERE LOWER(c.email) LIKE LOWER(CONCAT('%', :email, '%'))")
    List<Company> findCompaniesByEmail(@Param("email") String email);

    @Query("SELECT c FROM Company c WHERE c.phoneForCalls = :phoneForCalls")
    List<Company> findCompaniesByPhoneForCalls(@Param("phoneForCalls") Long phoneForCalls);


    @Query("SELECT c FROM Company c WHERE STR(c.inn) LIKE CONCAT('%', :inn, '%')")
    List<Company> findCompaniesByInn(@Param("inn") Long inn);

}
