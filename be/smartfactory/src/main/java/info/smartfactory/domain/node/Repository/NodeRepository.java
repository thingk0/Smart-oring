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
            value = "SELECT * FROM storage s")
    List<Storage> getStorage();

    @Query(nativeQuery = true,
            value = "SELECT * FROM charger")
    List<Charger> getCharger();

    @Query(nativeQuery = true,
            value = "SELECT * FROM destination")
    List<Destination> getDestination();

    @Query(nativeQuery = true,
            value = "DELETE FROM node")
    void deleteMap();
}
