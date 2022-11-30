package anwendung.controller;

import api.exceptions.ZuVielePersonException;
import api.person.PersonRelatedAPI;
import api.person.PersonRelatedImpl;
import javassist.tools.rmi.ObjectNotFoundException;
import models.modelsHibernate.person.Person;
import models.modelsHibernate.person.knows.KnowsPath;
import models.modelsHibernate.tag.Tag;
import org.hibernate.Session;
import utils.HibernateUtil;

import java.util.List;
import java.util.Set;

import static utils.api.Helper.*;

public class PersonenApiController {
    private Session session;
    private PersonRelatedAPI personApi;

    public PersonenApiController() {
        session = HibernateUtil.openSession();
        personApi = new PersonRelatedImpl(session);
    }

    public void printMöglichkeiten() {
        System.out.println();
        System.out.println("Welche Abfrage möchten Sie tätigen?");
        System.out.println("(1): getProfile");
        System.out.println("(2): getCommonInterestsOfMyFriends");
        System.out.println("(3): getCommonFriends");
        System.out.println("(4): getPersonsWithMostCommonInterests");
        System.out.println("(5): getJobRecommendation");
        System.out.println("(6): getShorthestFriendshipPath");
        System.out.println("(7): Zeitvergleich Criteria Api / Java");
        System.out.println("(x): Zurück zur Api Auswahl");
    }


    public void run() {
        String input = null;
        do {
            printMöglichkeiten();
            input = getInput();
            switch (input) {
                case "1":
                    getProfile();
                    break;
                case "2":
                    getCommonInterestsOfMyFriends();
                    break;
                case "3":
                    getCommonFriends();
                    break;
                case "4":
                    getPersonsWithMostCommonInterests();
                    break;
                case "5":
                    getJobRecommendation();
                    break;
                case "6":
                    getShorthestFriendshipPath();
                    break;
                case "7":
                    makeTimeComparison();
                    break;
                case "x":
                    break;
                default:
                    System.out.println("Eingabe konnte nicht zugeordnet werden");
            }
        } while (!input.equals("x"));
        session.close();
    }

    public Long getID() throws NumberFormatException {
        System.out.println("Bitte geben Sie eine ID ein");
        return Long.parseLong(getInput());
    }

    public void getProfile() {
        try {
            Long id = getID();
            Person profile = personApi.getProfile(id);
            System.out.println(profile);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        } catch (ZuVielePersonException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void getCommonInterestsOfMyFriends() {
        try {
            Long id = getID();
            Set<Tag> interests = personApi.getCommonInterestsOfMyFriends(id);
            printSet(interests);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void getCommonFriends() {
        try {
            Long id = getID();
            Long friendId = getID();
            Set<Person> commonFriends = personApi.getCommonFriends(id, friendId);
            printSetPersonShort(commonFriends);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void getPersonsWithMostCommonInterests() {
        try {
            Long id = getID();
            Person personsWithMostCommonInterests = personApi.getPersonsWithMostCommonInterests(id);
            System.out.println(personsWithMostCommonInterests.toStringShort());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void getJobRecommendation() {
        try {
            Long id = getID();
            List<Set> jobRecommendation = personApi.getJobRecommendation(id);

            System.out.println("Jobvorschläge für Company");
            printSet(jobRecommendation.get(0));
            System.out.println();

            System.out.println("Jobvorschläge für University");
            printSet(jobRecommendation.get(1));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void getShorthestFriendshipPath() {
        try {
            Long id = getID();
            Long friendId = getID();
            KnowsPath shorthestFriendshipPath = personApi.getShorthestFriendshipPath(id, friendId);
            if (shorthestFriendshipPath == null) {
                System.out.println("Es konnte kein Pfad gefunden werden, der diese beiden IDs verbindet");
            } else {
                System.out.println(shorthestFriendshipPath);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void makeTimeComparison() {
        try {
            System.out.println("Zeitvergleich getCommonInterestsOfMyFriends");
            Long id = getID();

            long startTime = System.currentTimeMillis();
            personApi.getCommonInterestsOfMyFriends(id);
            long endTime = System.currentTimeMillis();
            long commonFriendsTime = endTime - startTime;

            startTime = System.currentTimeMillis();
            personApi.getCommonInterestsOfMyFriendsJAVA(id);
            endTime = System.currentTimeMillis();
            long commonFriendsJavaTime = endTime - startTime;

            System.out.println("Criteria Api: " + commonFriendsTime + "ms");
            System.out.println("Java: " + commonFriendsJavaTime + "ms");
            System.out.println("Differenz: " + (commonFriendsJavaTime - commonFriendsTime) + "ms");
            System.out.println();

            System.out.println("Zeitvergleich getPersonsWithMostCommonInterests");
            id = getID();

            startTime = System.currentTimeMillis();
            personApi.getPersonsWithMostCommonInterests(id);
            endTime = System.currentTimeMillis();
            long personWithMostCommonInterestsTime = endTime - startTime;

            startTime = System.currentTimeMillis();
            personApi.getPersonsWithMostCommonInterestsJAVA(id);
            endTime = System.currentTimeMillis();
            long personWithMostCommonInterestsJavaTime = endTime - startTime;

            System.out.println("Criteria Api: " + personWithMostCommonInterestsTime + "ms");
            System.out.println("Java: " + personWithMostCommonInterestsJavaTime + "ms");
            System.out.println("Differenz: " + (personWithMostCommonInterestsJavaTime - personWithMostCommonInterestsTime) + "ms");
            System.out.println();

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (ZuVielePersonException e) {
            e.printStackTrace();
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        }
    }
}
