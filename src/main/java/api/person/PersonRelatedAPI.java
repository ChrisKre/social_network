package api.person;

import api.exceptions.ZuVielePersonException;
import javassist.tools.rmi.ObjectNotFoundException;
import models.modelsHibernate.person.Person;
import models.modelsHibernate.person.knows.KnowsPath;
import models.modelsHibernate.tag.Tag;

import java.util.List;
import java.util.Set;

public interface PersonRelatedAPI {
    Person getProfile(Long id) throws ObjectNotFoundException, ZuVielePersonException;

    Set<Tag> getCommonInterestsOfMyFriends(Long id);

    Set<Person> getCommonFriends(Long id, Long friendId);

    Person getPersonsWithMostCommonInterests(Long id);

    List<Set> getJobRecommendation(Long id);

    KnowsPath getShorthestFriendshipPath(Long id, Long personId);


    //JAVA METHODEN FÜR ZEITVERGLEICH
    public Set<Tag> getCommonInterestsOfMyFriendsJAVA(Long id) throws ZuVielePersonException, ObjectNotFoundException;

    public Set<Person> getPersonsWithMostCommonInterestsJAVA(Long id) throws ZuVielePersonException, ObjectNotFoundException;

}
