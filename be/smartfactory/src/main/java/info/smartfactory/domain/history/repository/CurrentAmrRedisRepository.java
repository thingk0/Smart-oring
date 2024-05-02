package info.smartfactory.domain.history.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import info.smartfactory.domain.history.entity.AmrHistory;

@Repository
public interface CurrentAmrRedisRepository extends CrudRepository<AmrHistory, String> {

}
