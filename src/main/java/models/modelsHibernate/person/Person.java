package models.modelsHibernate.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.modelsHibernate.forum.Forum;
import models.modelsHibernate.forum.hasMember.HasMember;
import models.modelsHibernate.message.Comment;
import models.modelsHibernate.message.Post;
import models.modelsHibernate.person.knows.Knows;
import models.modelsHibernate.person.likes.LikesComment;
import models.modelsHibernate.person.study.StudyAt;
import models.modelsHibernate.person.work.WorkAt;
import models.modelsHibernate.place.City;
import models.modelsHibernate.tag.Tag;
import org.apache.commons.collections.CollectionUtils;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Person")
public class Person {
    @Id
    private Long pid;
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate birthday;
    private LocalDateTime creationDate;
    private String locationIP;
    private String browserUsed;

    @ManyToOne
    @JoinColumn(name = "isLocatedIn", nullable = false)
    private City isLocatedIn;

    @OneToMany(mappedBy = "person")
    private Set<Knows> follows;

    @OneToMany(mappedBy = "knows")
    private Set<Knows> isFollowed;

    @OneToMany(mappedBy = "person")
    private Set<WorkAt> workAt;

    @OneToMany(mappedBy = "person")
    private Set<StudyAt> studyAt;

    @OneToMany(mappedBy = "person")
    private Set<HasMember> isMemberOf;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "personHasInterest",
            joinColumns = {@JoinColumn(name = "pid")},
            inverseJoinColumns = {@JoinColumn(name = "tid")}
    )
    private Set<Tag> interests;

    @OneToMany(mappedBy = "moderator")
    private Set<Forum> forum;

    @OneToMany(mappedBy = "person")
    private Set<LikesComment> commentsLiked;

    @OneToMany(mappedBy = "creator")
    private Set<Comment> commentsCreated;

    @OneToMany(mappedBy = "creator")
    private Set<Post> postsCreated;

    public String toString() {
        return "Firstname: " + firstName + ", Lastname: " + lastName + "\n" +
                "Geschlecht: " + gender + ", " + "Geburstag: " + birthday + "\n" +
                "Mitglied seit: " + creationDate + ", Wohnort: " + isLocatedIn.getName();
    }

    public String toStringShort() {
        return "ID: " + pid + ", Firstname: " + firstName + ", Lastname: " + lastName;
    }

    public Set<Person> getPersonsWithMostCommonInterests(List<Person> persons) {
        Hashtable<Integer, List<Person>> counts = new Hashtable<Integer, List<Person>>();
        for (Person person : persons) {
            int count = countCommonInterests(getInterests(), person.getInterests());
            List<Person> values = counts.get(count);
            if (values == null) {
                List<Person> newList = new ArrayList<>();
                newList.add(person);
                counts.put(count, newList);
            } else {
                values.add(values.size() - 1, person);
                counts.put(count, values);
            }
        }
        return new HashSet<Person>(counts.get(Collections.max(counts.keySet())));
    }

    public static Integer countCommonInterests(Set<Tag> interests, Set<Tag> foreignInterests) {
        Collection intersection = CollectionUtils.intersection(interests, foreignInterests);
        return intersection.size();
    }

    public Set<Person> getCommonFriends(Set<Knows> friends) {
        Set<Person> commonFriends = new HashSet<>();
        for (Knows person : getFollows()) {
            for (Knows friend : friends) {
                if (person.getKnows().getPid().equals(friend.getKnows().getPid())) {
                    commonFriends.add(person.getKnows());
                }
            }
        }
        return commonFriends;
    }

    public Set<Tag> getCommonInterestsFromFriends(Set<Knows> friends, Set<Tag> interests) {
        Set<Tag> commonInterests = new HashSet<>();
        for (Knows friend : friends) {
            for (Tag interest : friend.getPerson().getInterests()) {
                if (interests.contains(interest)) {
                    commonInterests.add(interest);
                }
            }
        }
        return commonInterests;
    }

}


