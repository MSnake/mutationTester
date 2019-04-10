package ru.mai.diplom.tester.db.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import ru.mai.diplom.tester.db.model.MutationData;

import java.util.Optional;

/**
 * Репозиторий для работы со списком с информацией о преобразованиях
 */
@Repository
public interface MutationDataDao extends JpaRepository<MutationData, Long>, JpaSpecificationExecutor<MutationData> {

    Optional<MutationData> findByMd5Data(String md5Data);
}
