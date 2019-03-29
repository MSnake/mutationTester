package ru.mai.diplom.tester.db.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.mai.diplom.tester.db.model.DocumentTypeData;
import ru.mai.diplom.tester.db.model.DocumentType;


import java.util.Optional;

/**
 * Read-only репозиторий для работы со списком типов документов.
 */
public interface DocumentTypeDao extends JpaRepository<DocumentTypeData, String>, JpaSpecificationExecutor<DocumentTypeData> {
    Optional<DocumentTypeData> findByDocType(DocumentType docType);
}
