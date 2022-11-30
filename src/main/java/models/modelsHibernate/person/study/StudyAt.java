package models.modelsHibernate.person.study;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.modelsHibernate.organisation.University;
import models.modelsHibernate.person.Person;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "StudyAt")
public class StudyAt {
    @EmbeddedId
    private StudyAtId id;

    private Integer classYear;

    @ManyToOne
    @MapsId("pid")
    @JoinColumn(name = "pid")
    private Person person;

    @ManyToOne
    @MapsId("oid")
    @JoinColumn(name = "oid")
    private University university;

    public StudyAt(Person person, University university, Integer classYear) {
        this.person = person;
        this.university = university;
        this.classYear = classYear;
        this.id = new StudyAtId(person.getPid(), university.getOid());
    }
}
