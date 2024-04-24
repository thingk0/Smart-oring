package info.smartfactory.domain.node.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "charger")
public class Charger {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    //TODO [JPA Buddy] generate columns from DB
}