package info.smartfactory.domain.node.Repository;

import info.smartfactory.domain.node.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DestinationRepository extends JpaRepository<Destination, Integer> {
    @Query("SELECT d FROM Destination d")
    List<Destination> getDestination();

}
