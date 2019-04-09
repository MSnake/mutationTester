package ru.mai.diplom.tester.db.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.mai.diplom.tester.db.model.MutationData;

/**
 * Репозиторий для работы со списком с информацией о преобразованиях
 */
@Repository
public interface MutationDataDao extends JpaRepository<MutationData, Long>, JpaSpecificationExecutor<MutationData> {
}
