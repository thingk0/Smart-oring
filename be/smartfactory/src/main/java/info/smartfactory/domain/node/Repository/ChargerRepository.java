package info.smartfactory.domain.node.Repository;

import info.smartfactory.domain.node.entity.Charger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChargerRepository extends JpaRepository<Charger, Integer> {
    @Query("SELECT c FROM Charger c JOIN FETCH c.node")
    List<Charger> getCharger();

}
