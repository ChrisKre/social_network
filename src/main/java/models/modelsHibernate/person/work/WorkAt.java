package models.modelsHibernate.person.work;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.modelsHibernate.organisation.Company;
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
@EqualsAndHashCode
@Table(name = "WorkAt")
public class WorkAt {
    @EmbeddedId
    private WorkAtId id;

    private Integer workFrom;

    @ManyToOne
    @MapsId("pid")
    @JoinColumn(name = "pid")
    private Person person;

    @ManyToOne
    @MapsId("oid")
    @JoinColumn(name = "oid")
    private Company company;

    public WorkAt(Person person, Company company, Integer workFrom) {
        this.person = person;
        this.company = company;
        this.workFrom = workFrom;
        this.id = new WorkAtId(person.getPid(), company.getOid());
    }
}
