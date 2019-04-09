package ru.mai.diplom.tester.db.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Описание таблицы с информацией о результате тестирования
 */
@Getter
@Setter
@Entity
@Table(name = "TEST_RESULT_DATA")
public class TestResultData {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "TEST_CODE_DATA_ID", nullable = false, updatable = false)
    private TestCodeData testCodeData;

    @OneToOne(optional = false)
    @JoinColumn(name = "MUTATION_DATA_ID", nullable = false, updatable = false)
    private MutationData mutationData;

    @Column(name = "RESULT_TEXT")
    private String resultText;

    @Column(name = "STATUS")
    private TestResultStatusType status;
}
