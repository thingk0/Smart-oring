package info.smartfactory.domain.history.repository;

import info.smartfactory.domain.history.entity.AmrHistory;
import info.smartfactory.domain.history.repository.custom.AmrHistoryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmrHistoryRepository extends JpaRepository<AmrHistory, Long>, AmrHistoryRepositoryCustom {

}