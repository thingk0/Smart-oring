package info.smartfactory.domain.node.repository;

import info.smartfactory.domain.node.entity.Node;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NodeRepository extends JpaRepository<Node, Long> {

}