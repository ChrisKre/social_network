package api.statistic;

import models.modelsHibernate.message.Comment;
import models.modelsHibernate.place.Country;
import models.modelsHibernate.tag.TagClass;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StatisticImpl implements StatisticAPI {
    Session session;

    public StatisticImpl(Session session) {
        this.session = session;
    }

    @Override
    public void getTagClassHierarchy() {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<TagClass> cr = cb.createQuery(TagClass.class);
        Root<TagClass> root = cr.from(TagClass.class);
        cr.select(root);
        cr.where(cb.isEmpty(root.get("isSubclassOf")));
        Query<TagClass> query = session.createQuery(cr);
        List<TagClass> results = query.getResultList();

        for (int i = 0; i < results.size(); i++) {
            System.out.println(i + " " + results.get(i).getName());
            getNextLevel(results.get(i), "" + i + "");
        }
    }

    void getNextLevel(TagClass member, String level) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<TagClass> cr = cb.createQuery(TagClass.class);
        Root<TagClass> root = cr.from(TagClass.class);
        cr.select(root);
        cr.where(cb.isMember(member, root.get("isSubclassOf")));
        Query<TagClass> query = session.createQuery(cr);
        List<TagClass> results = query.getResultList();

        if (results.size() == 0) {
            return;
        } else {
            for (int i = 0; i < results.size(); i++) {
                String nextLevel = level + "." + (i + 1);
                System.out.println(nextLevel + " " + results.get(i).getName());
                getNextLevel(results.get(i), nextLevel);
            }
        }
    }

    @Override
    public Set<Comment> getPopularComments(Integer k) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Comment> cr = cb.createQuery(Comment.class);
        Root<Comment> root = cr.from(Comment.class);
        cr.select(root)
                .where(cb.greaterThanOrEqualTo(cb.size(root.get("isLikedBy")), k));

        Query<Comment> query = session.createQuery(cr);
        List<Comment> results = query.getResultList();
        return new HashSet<>(results);
    }

    @Override
    public Country getCountryWithMostTraffic() {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Country> cr = cb.createQuery(Country.class);
        Root<Country> root = cr.from(Country.class);

        cr.select(root).orderBy(
                cb.desc(
                        cb.sum(
                                cb.size(root.get("posts")), cb.size(root.get("comments")))));

        Query<Country> query = session.createQuery(cr);
        List<Country> results = query.getResultList();
        return results.get(0);
    }


    //JAVA METHODE FÜR ZEITVERGLEICH
    public Country getCountryWithMostTrafficJAVA() {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Country> cr = cb.createQuery(Country.class);
        Root<Country> root = cr.from(Country.class);
        cr.select(root);
        Query<Country> query = session.createQuery(cr);
        List<Country> results = query.getResultList();
        Country countryWithMostTraffic = results.get(0).getCountryWithMostTraffic(results);
        return countryWithMostTraffic;
    }
}
