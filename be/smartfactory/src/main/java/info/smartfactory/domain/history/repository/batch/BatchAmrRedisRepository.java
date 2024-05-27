package info.smartfactory.domain.history.repository.batch;

import info.smartfactory.domain.history.dto.BatchAmrInfoRedisDto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BatchAmrRedisRepository extends CrudRepository<BatchAmrInfoRedisDto, String> {

    @Override
    List<BatchAmrInfoRedisDto> findAll();
}
