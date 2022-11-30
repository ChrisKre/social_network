package models.modelsHibernate.place;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.modelsHibernate.message.Comment;
import models.modelsHibernate.message.Post;
import models.modelsHibernate.organisation.Company;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Country")
public class Country extends Place {

    @OneToMany(mappedBy = "country")
    private Set<City> cities;

    @ManyToOne
    @JoinColumn(name = "isPartOf", nullable = false)
    private Continent continent;

    @OneToMany(mappedBy = "country")
    private Set<Company> companies;

    @OneToMany(mappedBy = "isLocatedIn")
    private Set<Comment> comments;

    @OneToMany(mappedBy = "isLocatedIn")
    private Set<Post> posts;

    public String toString() {
        return "ID: " + getPlID() + ", Name: " + getName() + "\n" +
                "Anzahl Kommentare: " + getComments().size() + ", Anzahl Posts: " + getPosts().size() + "\n" +
                "Summe Kommentare und Posts: " + getSumOfPostComments();
    }

    public int getSumOfPostComments() {
        return getComments().size() + getPosts().size();
    }

    public Country getCountryWithMostTraffic(List<Country> countryList) {
        int max = 0;
        for (int i = 1; i < countryList.size(); i++) {
            if (countryList.get(i).getSumOfPostComments() > countryList.get(max).getSumOfPostComments()) {
                max = i;
            }
        }
        return countryList.get(max);
    }
}
