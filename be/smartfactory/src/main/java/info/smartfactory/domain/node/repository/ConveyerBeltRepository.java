package info.smartfactory.domain.node.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import info.smartfactory.domain.node.entity.type.ConveyorBelt;

public interface ConveyerBeltRepository extends JpaRepository<ConveyorBelt, Long> {
}
