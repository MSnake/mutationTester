package ru.mai.diplom.tester.db.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import ru.mai.diplom.tester.db.model.TestCodeData;

import java.util.Optional;

/**
 * Репозиторий для работы со списком с информацией о коде тестирования
 */
@Repository
public interface TestCodeDataDao extends JpaRepository<TestCodeData, Long>, JpaSpecificationExecutor<TestCodeData> {

    Optional<TestCodeData> findByMd5Data(String md5Data);
}
