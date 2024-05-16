package info.smartfactory.domain.node.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import info.smartfactory.domain.node.entity.type.ConveyorBelt;

public interface ConveyorBeltRepository extends JpaRepository<ConveyorBelt, Long> {

	List<ConveyorBelt> findByIsInteractiveTrue();


}
