package models.modelsHibernate.person.knows;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "KnowsPath")
public class KnowsPath {
    @Id
    private Long start;
    private Long ende;
    private Integer länge;
    public String pfad;

    public String toString() {
        return "Start ID: " + start + ", Ende ID: " + ende +
                ", Distanz:" + länge + "\nPfad: " + pfad;
    }
}
