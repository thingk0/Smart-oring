package info.smartfactory.domain.history.repository.live;

import info.smartfactory.domain.history.repository.CurrentAmrInfoRedisDto;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentAmrRedisRepository extends CrudRepository<CurrentAmrInfoRedisDto, String> {

    @Override
    List<CurrentAmrInfoRedisDto> findAll();
}
