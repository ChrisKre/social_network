package models.modelsHibernate.person.knows;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.modelsHibernate.person.Person;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Knows")
public class Knows {
    @EmbeddedId
    private KnowsId id;

    @ManyToOne
    @MapsId("pid")
    @JoinColumn(name = "pid")
    private Person person;

    @ManyToOne
    @MapsId("pid")
    @JoinColumn(name = "knows")
    private Person knows;

    private LocalDateTime creationDate;

    public Knows(Person person, Person knows, LocalDateTime creationDate) {
        this.person = person;
        this.knows = knows;
        this.creationDate = creationDate;
        this.id = new KnowsId(person.getPid(), knows.getPid());
    }


}
