package info.smartfactory.domain.history.repository.live;

import info.smartfactory.domain.history.dto.CurrentAmrInfoRedisDto;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface CurrentAmrRedisRepository extends CrudRepository<CurrentAmrInfoRedisDto, String> {

    @Override
    List<CurrentAmrInfoRedisDto> findAll();
}
