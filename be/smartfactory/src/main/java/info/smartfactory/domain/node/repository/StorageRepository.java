package info.smartfactory.domain.node.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import info.smartfactory.domain.node.entity.Storage;

public interface StorageRepository extends JpaRepository<Storage, Long> {
}
