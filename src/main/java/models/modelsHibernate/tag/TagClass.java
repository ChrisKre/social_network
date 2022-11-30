package models.modelsHibernate.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TagClass")
public class TagClass {
    @Id
    private Long tcID;
    private String name;

    @ManyToMany(mappedBy = "types")
    private Set<Tag> tags;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "tagisSubclassOf",
            joinColumns = @JoinColumn(name = "tcID", referencedColumnName = "tcID"),
            inverseJoinColumns = @JoinColumn(name = "isSubclassOf", referencedColumnName = "tcID")
    )
    private Set<TagClass> isSubclassOf;
}
