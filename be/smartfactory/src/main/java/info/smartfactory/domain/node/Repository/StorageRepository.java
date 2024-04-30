package info.smartfactory.domain.node.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import info.smartfactory.domain.node.entity.Storage;

public interface StorageRepository extends JpaRepository<Storage, Integer> {
}
