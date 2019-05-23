package ru.mai.diplom.tester.db.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Описание таблицы с информацией об исходном коде
 */
@Getter
@Setter
@Entity
@Table(name = "SOURCE_CODE_DATA")
public class SourceCodeData {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CODE_TEXT")
    private String codeText;

    @Column(name = "MD5")
    private String md5Data;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sourceCodeData", fetch = FetchType.EAGER)
    private Set<MutationData> mutationDataList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sourceCodeData", fetch = FetchType.EAGER)
    private Set<TestCodeData> testCodeDataList;

    public void addMutationData(MutationData data) {
        if (data == null) {
            return;
        } else {
            if (mutationDataList == null) {
                mutationDataList = new HashSet<MutationData>();
            }
            Optional<MutationData> founded = mutationDataList.stream()
                    .filter(mutationData -> mutationData.getMd5Data().equalsIgnoreCase(data.getMd5Data())).findFirst();
            if (!founded.isPresent()) {
                mutationDataList.add(data);
                data.setSourceCodeData(this);
            }
        }
    }

    public void addTestCodeData(TestCodeData data) {
        if (data == null) {
            return;
        } else {
            if (testCodeDataList == null) {
                testCodeDataList = new HashSet<TestCodeData>();
            }
            Optional<TestCodeData> founded = testCodeDataList.stream()
                    .filter(testCode -> testCode.getMd5Data().equalsIgnoreCase(data.getMd5Data())).findFirst();
            if (!founded.isPresent()) {
                testCodeDataList.add(data);
                data.setSourceCodeData(this);
            }
        }
    }
}
