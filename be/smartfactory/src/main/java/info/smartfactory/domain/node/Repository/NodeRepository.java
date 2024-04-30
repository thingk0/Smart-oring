package info.smartfactory.domain.node.Repository;

import info.smartfactory.domain.node.entity.Charger;
import info.smartfactory.domain.node.entity.Destination;
import info.smartfactory.domain.node.entity.Node;
import info.smartfactory.domain.node.entity.Storage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
@Repository
public interface NodeRepository extends JpaRepository<Node, Integer>{
    @Query(nativeQuery = true,
            value = "SELECT s.id, n.*, s.entrance_direction " +
                    "FROM storage s join node n " +
                    "ON s.node_id = n.id")
    List<Storage> getStorage();

    @Query(nativeQuery = true,
            value = "SELECT s.id, n.*, s.entrance_direction " +
                    "FROM storage s join node n " +
                    "ON s.node_id = n.id")
    List<Charger> getCharger();

    @Query(nativeQuery = true,
            value = "SELECT s.id, n.*, s.entrance_direction " +
                    "FROM storage s join node n " +
                    "ON s.node_id = n.id")
    List<Destination> getDestination();
}
