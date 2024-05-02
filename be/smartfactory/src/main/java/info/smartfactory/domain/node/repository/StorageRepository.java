package info.smartfactory.domain.node.repository;

import info.smartfactory.domain.node.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StorageRepository extends JpaRepository<Storage, Integer> {
    @Query("SELECT s FROM Storage s")
    List<Storage> getStorage();
}