package models.modelsHibernate.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.modelsHibernate.forum.Forum;
import models.modelsHibernate.person.Person;
import models.modelsHibernate.place.Country;
import models.modelsHibernate.tag.Tag;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Post")
public class Post implements Message {
    @Id
    private Long poID;
    private LocalDateTime creationDate;
    private String locationIP;
    private String browserUsed;
    private String content;
    private Integer length;
    private String language;
    private String imageFile;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "posthastag",
            joinColumns = {@JoinColumn(name = "poID")},
            inverseJoinColumns = {@JoinColumn(name = "tID")}
    )
    private Set<Tag> tags;

    @ManyToOne
    @JoinColumn(name = "plID", nullable = false)
    private Country isLocatedIn;

    @ManyToOne
    @JoinColumn(name = "hasCreator", nullable = false)
    private Person creator;

    @ManyToOne
    @JoinColumn(name = "fid", nullable = false)
    private Forum containerOfForum;

    @OneToMany(mappedBy = "relatedPost")
    private Set<Comment> replies;
}
