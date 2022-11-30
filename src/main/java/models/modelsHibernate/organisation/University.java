package models.modelsHibernate.organisation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.modelsHibernate.person.study.StudyAt;
import models.modelsHibernate.place.City;

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
@Table(name = "University")
public class University extends Organisation {
    @ManyToOne
    @JoinColumn(name = "isLocatedIn", nullable = false)
    private City city;

    @OneToMany(mappedBy = "university")
    public Set<StudyAt> students;

}
