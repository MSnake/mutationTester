package ru.mai.diplom.tester.db.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.mai.diplom.tester.db.model.TestCodeData;
import ru.mai.diplom.tester.db.model.TestResultData;

/**
 * Репозиторий для работы со списком с информацией о коде тестирования
 */
@Repository
public interface TestResultDataDao extends JpaRepository<TestResultData, Long>, JpaSpecificationExecutor<TestResultData> {
}
