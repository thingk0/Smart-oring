package info.smartfactory.domain.history.repository.live;

import info.smartfactory.domain.history.repository.CurrentAmrInfoRedisDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import info.smartfactory.domain.history.entity.AmrHistory;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrentAmrRedisRepository extends CrudRepository<CurrentAmrInfoRedisDto, String> {
    @Override
    List<CurrentAmrInfoRedisDto> findAll();
}
