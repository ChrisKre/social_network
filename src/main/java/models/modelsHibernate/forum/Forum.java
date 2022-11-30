package models.modelsHibernate.forum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.modelsHibernate.forum.hasMember.HasMember;
import models.modelsHibernate.message.Post;
import models.modelsHibernate.person.Person;
import models.modelsHibernate.tag.Tag;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Forum")
public class Forum {
    @Id
    private long fID;
    private String title;
    private LocalDateTime creationDate;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "HasTag",
            joinColumns = {@JoinColumn(name = "fID")},
            inverseJoinColumns = {@JoinColumn(name = "tid")}
    )
    private Set<Tag> tags;

    @ManyToOne
    @JoinColumn(name = "hasModerator", referencedColumnName = "pid", nullable = false)
    private Person moderator;

    @OneToMany(mappedBy = "forum")
    public Set<HasMember> hasMembers;

    @OneToMany(mappedBy = "containerOfForum")
    public Set<Post> posts;
}
