package info.smartfactory.domain.node.repository;

import info.smartfactory.domain.node.entity.type.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageRepository extends JpaRepository<Storage, Long> {

}
