package ru.mai.diplom.tester.db.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mai.diplom.tester.db.model.SourceCodeData;
import ru.mai.diplom.tester.db.model.TestCodeData;


import java.util.Optional;

/**
 * Репозиторий для работы со списком с информацией об исходном коде
 */
@Repository
public interface SourceCodeDataDao extends JpaRepository<SourceCodeData, Long>, JpaSpecificationExecutor<SourceCodeData> {

    @Query(value = "SELECT nextval('SOURCE_CODE_DATA_SEQ')", nativeQuery = true)
    long getNextSourceCodeId();
}
