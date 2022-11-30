package models.modelsHibernate.person.likes;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.modelsHibernate.message.Comment;
import models.modelsHibernate.person.Person;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "personlikescomment")
public class LikesComment {
    @EmbeddedId
    private LikesCommentId id;

    private LocalDateTime creationDate;

    @ManyToOne
    @MapsId("pid")
    @JoinColumn(name = "pid")
    private Person person;

    @ManyToOne
    @MapsId("coid")
    @JoinColumn(name = "coid")
    private Comment comment;


    public LikesComment(Person person, Comment comment, LocalDateTime creationDate) {
        this.person = person;
        this.comment = comment;
        this.creationDate = creationDate;
        this.id = new LikesCommentId(person.getPid(), comment.getCoID());
    }
}
