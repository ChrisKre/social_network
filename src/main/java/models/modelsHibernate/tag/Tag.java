package models.modelsHibernate.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.modelsHibernate.forum.Forum;
import models.modelsHibernate.message.Comment;
import models.modelsHibernate.message.Post;
import models.modelsHibernate.person.Person;

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
@Table(name = "Tag")
public class Tag {
    @Id
    private long tID;
    private String name;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "tagHasType",
            joinColumns = {@JoinColumn(name = "tid")},
            inverseJoinColumns = {@JoinColumn(name = "tcid")}
    )
    private Set<TagClass> types;

    @ManyToMany(mappedBy = "interests")
    private Set<Person> persons;

    @ManyToMany(mappedBy = "tags")
    private Set<Forum> forums;

    @ManyToMany(mappedBy = "tags")
    private Set<Comment> comments;

    @ManyToMany(mappedBy = "tags")
    private Set<Post> posts;

    public String toString() {
        return "ID: " + tID + ", " + " Name: " + name;
    }
}
