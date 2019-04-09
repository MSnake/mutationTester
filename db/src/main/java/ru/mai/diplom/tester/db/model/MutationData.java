package ru.mai.diplom.tester.db.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import ru.mai.diplom.tester.db.types.JsonStringUserType;

import javax.persistence.*;

/**
 * Описание таблицы с информацией о преобразованиях
 */
@Getter
@Setter
@Entity
@Table(name = "MUTATION_DATA")
@TypeDefs({@TypeDef(name = "JsonStringObject", typeClass = JsonStringUserType.class)})
public class MutationData {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CODE_TEXT")
    private String codeText;

    @Column(name = "MD5")
    private String md5Data;

    @Column(name = "OPTIONS")
    @Type(type = "JsonStringObject")
    private String jsonData;

//    @ManyToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
//    @JoinColumn(name = "SOURCE_CODE_ID")
    @ManyToOne
    @JoinColumn(name = "SOURCE_CODE_ID")
    private SourceCodeData sourceCodeData;

//    @ManyToOne(optional = false, fetch = FetchType.LAZY)
//    @JoinColumn(name = "TEST_CODE_ID", nullable = false)
//    private TestCodeData testCodeData;
}
