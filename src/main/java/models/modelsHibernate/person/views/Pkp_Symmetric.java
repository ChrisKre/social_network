package models.modelsHibernate.person.views;

import lombok.NoArgsConstructor;
import models.modelsHibernate.person.Person;
import models.modelsHibernate.person.knows.KnowsId;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
@Immutable
@NoArgsConstructor
@Subselect("Select * from pkp_symmetric")
public class Pkp_Symmetric {
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

    public Pkp_Symmetric(Person person, Person knows) {
        this.person = person;
        this.knows = knows;
        this.id = new KnowsId(person.getPid(), knows.getPid());
    }
}
