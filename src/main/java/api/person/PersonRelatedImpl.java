package api.person;

import api.exceptions.ZuVielePersonException;
import javassist.tools.rmi.ObjectNotFoundException;
import models.modelsHibernate.organisation.Company;
import models.modelsHibernate.organisation.University;
import models.modelsHibernate.person.Person;
import models.modelsHibernate.person.knows.Knows;
import models.modelsHibernate.person.knows.KnowsPath;
import models.modelsHibernate.person.study.StudyAt;
import models.modelsHibernate.person.views.Pkp_Symmetric;
import models.modelsHibernate.person.work.WorkAt;
import models.modelsHibernate.tag.Tag;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class PersonRelatedImpl implements PersonRelatedAPI {

    Session session;

    public PersonRelatedImpl(Session session) {
        this.session = session;
    }

    @Override
    public Person getProfile(Long id) throws ObjectNotFoundException, ZuVielePersonException {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Person> cr = cb.createQuery(Person.class);
        Root<Person> root = cr.from(Person.class);
        cr.select(root);
        cr.where(cb.equal(root.get("pid"), id));

        Query<Person> query = session.createQuery(cr);
        List<Person> results = query.getResultList();


        if (results.size() == 0) {
            throw new ObjectNotFoundException("Für diese ID:" + id + " ist keine Person vorhanden!");
        } else if (results.size() > 1) {
            throw new ZuVielePersonException("Für diese ID:" + id + " sind mehr als eine Person gefunden worden!");
        }
        return results.get(0);
    }

    @Override
    public Set<Tag> getCommonInterestsOfMyFriends(Long id) {
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<Tag> cr = cb.createQuery(Tag.class);
        Root<Tag> root = cr.from(Tag.class);
        Join<Tag, Person> personJoin = root.join("persons");

        Subquery subTag = cr.subquery(Tag.class);
        Root subRootTag = subTag.from(Tag.class);
        Join<Person, Tag> interests = subRootTag.join("persons");
        subTag.select(subRootTag)
                .where(cb.equal(interests.get("pid"), id));


        Subquery subFriend = cr.subquery(Pkp_Symmetric.class);
        Root subRootFriend = subFriend.from(Pkp_Symmetric.class);
        subFriend.select(subRootFriend.get("person"))
                .where(
                        cb.or(
                                cb.equal(subRootFriend.get("person"), id),
                                cb.equal(subRootFriend.get("knows"), id)));

        cr.select(root)
                .where(
                        cb.and(root.get("tID").in(subTag)),
                        cb.and(personJoin.get("pid").in(subFriend)),
                        cb.and(cb.notEqual(personJoin.get("pid"), id))
                ).groupBy(root.get("tID"));


        Query<Tag> query = session.createQuery(cr);
        List<Tag> results = query.getResultList();

        return new HashSet<>(results);
    }

    @Override
    public Set<Person> getCommonFriends(Long id, Long friendId) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Person> cr = cb.createQuery(Person.class);
        Root<Person> root = cr.from(Person.class);

        Subquery subFriend = cr.subquery(Pkp_Symmetric.class);
        Root subRootFriend = subFriend.from(Pkp_Symmetric.class);
        subFriend.select(subRootFriend.get("knows"))
                .where(subRootFriend.get("person").in(id, friendId))
                .groupBy(subRootFriend.get("knows"))
                .having(cb.greaterThanOrEqualTo(cb.count(subRootFriend.get("knows")), cb.toLong(cb.literal(2))));

        cr.select(root);
        cr.where(cb.and(root.get("pid").in(subFriend)));
        Query<Person> query = session.createQuery(cr);

        List<Person> results = query.getResultList();
        return new HashSet<>(results);
    }

    @Override
    public Person getPersonsWithMostCommonInterests(Long id) {
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<Person> cr = cb.createQuery(Person.class);
        Root<Person> root = cr.from(Person.class);
        Join<Person, Tag> tagJoin = root.join("interests");

        Subquery subTag = cr.subquery(Tag.class);
        Root subRootTag = subTag.from(Tag.class);
        Join<Person, Tag> interests = subRootTag.join("persons");
        subTag.select(subRootTag.get("tID"))
                .where(cb.equal(interests.get("pid"), id));

        Subquery subPerson = cr.subquery(Person.class);
        Root subRootPerson = subPerson.from(Person.class);
        subPerson.select(subRootPerson.get("pid"));

        cr.select(root)
                .where(
                        cb.and(tagJoin.get("tID").in(subTag)),
                        cb.and(root.get("pid").in(subPerson)),
                        cb.and(cb.notEqual(root.get("pid"), id))
                )
                .groupBy(root.get("pid"))
                .orderBy(cb.desc(cb.count(root.get("pid"))));

        Query<Person> query = session.createQuery(cr);
        List<Person> results = query.getResultList();

        return results.get(0);
    }

    @Override
    public List<Set> getJobRecommendation(Long id) {
        Set<Company> companyRecommendation = getCompanyRecommendation(id);
        Set<University> universityRecommendation = getUniversityRecommendation(id);
        List<Set> result = new ArrayList<>();
        result.add(companyRecommendation);
        result.add(universityRecommendation);
        return result;
    }

    public Set<University> getUniversityRecommendation(Long id) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<University> cr = cb.createQuery(University.class);
        Root<University> universityRoot = cr.from(University.class);

        Join<University, StudyAt> universityStudyAtJoin = universityRoot.join("students");
        Join<Knows, StudyAt> knows = universityStudyAtJoin.join("person");
        Join<Knows, Person> friend = knows.join("isFollowed");


        Subquery subUniversity = cr.subquery(University.class);
        Root subRootUniversity = subUniversity.from(University.class);
        Join<University, Person> workAtJoin = subRootUniversity.join("students");
        subUniversity.select(subRootUniversity.get("city"))
                .where(
                        cb.equal(workAtJoin.get("person"), id)
                );

        cr.select(universityRoot)
                .where(cb.and(
                        cb.equal(universityStudyAtJoin.get("person"), friend.get("knows")),
                        cb.equal(universityStudyAtJoin.get("university"), universityRoot.get("oid")),
                        universityRoot.get("city").in(subUniversity),
                        cb.equal(friend.get("person"), id)));

        Query<University> query = session.createQuery(cr);
        List<University> results = query.getResultList();

        return new HashSet<>(results);
    }

    public Set<Company> getCompanyRecommendation(Long id) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Company> cr = cb.createQuery(Company.class);
        Root<Company> companyRoot = cr.from(Company.class);

        Join<Company, WorkAt> companyWorkAtJoin = companyRoot.join("employees");
        Join<Knows, WorkAt> knows = companyWorkAtJoin.join("person");
        Join<Knows, Person> friend = knows.join("isFollowed");

        Subquery subCompanyMaxDate = cr.subquery(Integer.class);
        Root subRootCompanyDate = subCompanyMaxDate.from(Company.class);
        Join<Company, Person> workAtMaxJoin = subRootCompanyDate.join("employees");
        subCompanyMaxDate.select(cb.max(workAtMaxJoin.get("workFrom")))
                .where(
                        cb.and(cb.equal(workAtMaxJoin.get("person"), id)));

        Subquery subCompany = cr.subquery(Company.class);
        Root subRootCompany = subCompany.from(Company.class);
        Join<Company, Person> workAtJoin = subRootCompany.join("employees");
        subCompany.select(subRootCompany.get("country"))
                .where(
                        cb.equal(workAtJoin.get("person"), id),
                        cb.equal(workAtJoin.get("workFrom"), subCompanyMaxDate)
                );

        cr.select(companyRoot)
                .where(cb.and(
                        cb.equal(companyWorkAtJoin.get("person"), friend.get("knows")),
                        cb.equal(companyWorkAtJoin.get("company"), companyRoot.get("oid")),
                        companyRoot.get("country").in(subCompany),
                        cb.equal(friend.get("person"), id)));

        Query<Company> query = session.createQuery(cr);
        List<Company> results = query.getResultList();

        return new HashSet<>(results);
    }

    @Override
    public KnowsPath getShorthestFriendshipPath(Long id, Long personId) {

        List<Long> pfad = new ArrayList<>();
        Query query1 = session.createSQLQuery("CALL getShortestPathProcedure(:start, :ende, :länge, :pfad)")
                .addEntity(KnowsPath.class)
                .setParameter("start", id)
                .setParameter("ende", personId)
                .setParameter("länge", 0)
                .setParameter("pfad", pfad);


        List<KnowsPath> result = query1.list();
        return result.get(0);
    }


    // JAVA METHODEN ZUM VERGLEICH DER LAUFZEIT

    public Set<Tag> getCommonInterestsOfMyFriendsJAVA(Long id) throws ZuVielePersonException, ObjectNotFoundException {
        Person person = getProfile(id);
        Set<Tag> commonInterests = person.getCommonInterestsFromFriends(person.getFollows(), person.getInterests());
        commonInterests.addAll(person.getCommonInterestsFromFriends(person.getIsFollowed(), person.getInterests()));
        return commonInterests;
    }

    public List<Person> getPersonsOtherThanId(Long id) throws ObjectNotFoundException {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Person> cr = cb.createQuery(Person.class);
        Root<Person> root = cr.from(Person.class);
        cr.select(root);
        cr.where(cb.notEqual(root.get("pid"), id));
        Query<Person> query = session.createQuery(cr);
        List<Person> results = query.getResultList();
        if (results.size() == 0) {
            throw new ObjectNotFoundException("Es sind keine anderen Personen auffindbar!");
        }
        return results;
    }

    public Set<Person> getPersonsWithMostCommonInterestsJAVA(Long id) throws ZuVielePersonException, ObjectNotFoundException {
        Person person = getProfile(id);
        List<Person> persons = getPersonsOtherThanId(id);
        Set<Person> personsWithMostCommonInterests = person.getPersonsWithMostCommonInterests(persons);
        return personsWithMostCommonInterests;
    }

}
