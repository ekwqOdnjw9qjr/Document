package ru.lenazavupach.documentzxc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.lenazavupach.documentzxc.entity.Company;
import ru.lenazavupach.documentzxc.entity.Document;
import ru.lenazavupach.documentzxc.repository.CompanyRepository;
import ru.lenazavupach.documentzxc.repository.DocumentRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CompanyDocumentRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private DocumentRepository documentRepository;

    private Company company;
    private Document document1;
    private Document document2;

    @BeforeEach
    void setUp() {
        company = new Company();
        company.setName("Test Company");
        company.setAddress("Test Address");
        company.setEmail("test@example.com");
        company.setPhoneForCalls(1234567890L);
        company.setInn(9876543210L);

        document1 = new Document();
        document1.setTitle("Test Document 1");
        document1.setType("Test Type");
        document1.setDate(LocalDate.now());
        document1.setAuthor("Test Author");
        document1.setNumber(1L);
        document1.setCompany(company);

        document2 = new Document();
        document2.setTitle("Test Document 2");
        document2.setType("Test Type");
        document2.setDate(LocalDate.now());
        document2.setAuthor("Test Author");
        document2.setNumber(2L);
        document2.setCompany(company);
    }

    @Test
    void testCompanyDocumentRelationship() {
        // Save company and documents
        companyRepository.save(company);
        documentRepository.save(document1);
        documentRepository.save(document2);

        // Fetch company and verify documents
        Company savedCompany = companyRepository.findById(company.getId()).orElseThrow();
        List<Document> documents = documentRepository.findAllByCompanyId(savedCompany.getId());

        assertThat(documents).hasSize(2);
        assertThat(documents).contains(document1, document2);
    }
}
