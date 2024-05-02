package info.smartfactory.domain.bottleneck.repository;

import info.smartfactory.domain.bottleneck.entity.Bottleneck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BottleneckRepository extends JpaRepository<Bottleneck, Long> {

}