package models.modelsHibernate.place;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.modelsHibernate.organisation.University;
import models.modelsHibernate.person.Person;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "City")
public class City extends Place {

    @OneToMany(mappedBy = "isLocatedIn")
    private Set<Person> persons;

    @OneToMany(mappedBy = "city")
    private Set<University> universities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "isPartOf", nullable = false)
    private Country country;

}
