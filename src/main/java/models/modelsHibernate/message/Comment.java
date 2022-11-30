package models.modelsHibernate.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.modelsHibernate.person.Person;
import models.modelsHibernate.person.likes.LikesComment;
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
@Table(name = "Comment_")
public class Comment {
    @Id
    private long coID;
    private LocalDateTime creationDate;
    private String locationIP;
    private String browserUsed;
    private String content;
    private String length;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "commenthastag",
            joinColumns = {@JoinColumn(name = "coID")},
            inverseJoinColumns = {@JoinColumn(name = "tid")}
    )
    private Set<Tag> tags;

    @ManyToOne
    @JoinColumn(name = "replyOfComment")
    private Comment relatedComment;

    @OneToMany(mappedBy = "relatedComment")
    private Set<Comment> replies;

    @ManyToOne
    @JoinColumn(name = "replyOfPost")
    private Post relatedPost;

    @ManyToOne
    @JoinColumn(name = "plID", nullable = false)
    private Country isLocatedIn;

    @ManyToOne
    @JoinColumn(name = "hasCreator", nullable = false)
    private Person creator;

    @OneToMany(mappedBy = "comment")
    public Set<LikesComment> isLikedBy;

    public String toString() {
        return "ID: " + coID + ", Creator: " + creator.getFirstName() + " " + creator.getLastName();
    }
}
