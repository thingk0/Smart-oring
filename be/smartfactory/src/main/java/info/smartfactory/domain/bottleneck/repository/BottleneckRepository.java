package info.smartfactory.domain.bottleneck.repository;

import info.smartfactory.domain.bottleneck.entity.Bottleneck;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

public interface BottleneckRepository extends JpaRepository<Bottleneck, Long> {

    @NonNull
    @Query("SELECT b FROM Bottleneck b LEFT JOIN FETCH b.mission")
    List<Bottleneck> findAllWithMission();

}