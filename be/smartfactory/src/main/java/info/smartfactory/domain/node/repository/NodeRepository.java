package info.smartfactory.domain.node.repository;

import info.smartfactory.domain.node.entity.Node;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface NodeRepository extends JpaRepository<Node, Long>{
    @Query("SELECT n FROM Node n")
    List<Node> findAllNodes();
}
