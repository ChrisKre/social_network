package models.modelsHibernate.forum.hasMember;

import lombok.*;
import models.modelsHibernate.forum.Forum;
import models.modelsHibernate.organisation.University;
import models.modelsHibernate.person.Person;
import models.modelsHibernate.person.study.StudyAtId;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "HasMember")
public class HasMember {
    @EmbeddedId
    private HasMemberId id;

    private LocalDateTime joinDate;

    @ManyToOne
    @MapsId("pid")
    @JoinColumn(name = "pid")
    private Person person;

    @ManyToOne
    @MapsId("fid")
    @JoinColumn(name = "fid")
    private Forum forum;

    public HasMember(Person person, Forum forum, LocalDateTime joinDate) {
        this.person = person;
        this.forum = forum;
        this.joinDate = joinDate;
        this.id = new HasMemberId(person.getPid(), forum.getFID());
    }
}


