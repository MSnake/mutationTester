package ru.mai.diplom.tester.db.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Описание типа документа
 */
@Getter
@Setter
@Entity
@Table(name = "DOC_TYPES")
public class DocumentTypeData {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "ID")
    private DocumentType docType;

    @Column(name = "TYPE_NAME")
    private String typeName;
}
