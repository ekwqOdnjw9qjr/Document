package ru.lenazavupach.documentzxc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lenazavupach.documentzxc.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    Document findByTitle(String name);


}
