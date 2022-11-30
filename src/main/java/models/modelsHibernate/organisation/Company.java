package models.modelsHibernate.organisation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.modelsHibernate.person.work.WorkAt;
import models.modelsHibernate.place.Country;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "Company")
public class Company extends Organisation {
    @ManyToOne
    @JoinColumn(name = "isLocatedIn", nullable = false)
    private Country country;

    @OneToMany(mappedBy = "company")
    private Set<WorkAt> employees;

    public String toString() {
        return "ID:" + getOid() + ", Name: " + getName();

    }
}
