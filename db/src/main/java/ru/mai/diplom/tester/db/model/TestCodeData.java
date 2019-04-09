package ru.mai.diplom.tester.db.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Описание таблицы с информацией о коде тестирования
 */
@Getter
@Setter
@Entity
@Table(name = "TEST_CODE_DATA")
public class TestCodeData {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CODE_TEXT")
    private String codeText;

    @Column(name = "MD5")
    private String md5Data;

//    @ManyToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
//    @JoinColumn(name = "SOURCE_CODE_ID")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SOURCE_CODE_DATA_ID")
    private SourceCodeData sourceCodeData;
}
