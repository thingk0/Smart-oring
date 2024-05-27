package info.smartfactory.domain.node.repository;

import info.smartfactory.domain.node.entity.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NodeRepository extends JpaRepository<Node, Long> {

    @Query("""
        SELECT n
        FROM Node n
        WHERE n.xCoordinate = :xCoordinate
        AND n.yCoordinate = :yCoordinate
        """)
    Node getByXCoordinateAndYCoordinate(@Param("xCoordinate") Integer xCoordinate, @Param("yCoordinate") Integer yCoordinate);
}
