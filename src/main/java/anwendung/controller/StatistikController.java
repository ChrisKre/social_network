package anwendung.controller;

import api.statistic.StatisticAPI;
import api.statistic.StatisticImpl;
import models.modelsHibernate.message.Comment;
import models.modelsHibernate.place.Country;
import org.hibernate.Session;
import utils.HibernateUtil;

import java.util.Set;

import static utils.api.Helper.getInput;
import static utils.api.Helper.printSet;

public class StatistikController {
    private Session session;
    private StatisticAPI statisticAPI;

    public StatistikController() {
        session = HibernateUtil.openSession();
        statisticAPI = new StatisticImpl(session);
    }

    public void printMöglichkeiten() {
        System.out.println();
        System.out.println("Welche Abfrage möchten Sie tätigen?");
        System.out.println("(1): getTagClassHierarchy");
        System.out.println("(2): getPopularComments");
        System.out.println("(3): getCountrWithMostTraffic");
        System.out.println("(4): Zeitvergleich Criteria Api / Java");
        System.out.println("(x): Zurück zur Api Auswahl");
    }

    public void run() {
        String input = null;
        do {
            printMöglichkeiten();
            input = getInput();
            switch (input) {
                case "1":
                    getTagClassHierarchy();
                    break;
                case "2":
                    getPopularComments();
                    break;
                case "3":
                    getCountrWithMostTraffic();
                    break;
                case "4":
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

    public void getTagClassHierarchy() {
        statisticAPI.getTagClassHierarchy();
    }

    public void getPopularComments() {
        try {
            System.out.println("Bitte geben Sie die Mindestanzahl an likes ein:");
            String input = getInput();
            Set<Comment> popularComments = statisticAPI.getPopularComments(Integer.parseInt(input));
            printSet(popularComments);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void getCountrWithMostTraffic() {
        Country countryWithMostTraffic = statisticAPI.getCountryWithMostTraffic();
        System.out.println(countryWithMostTraffic);
    }

    public void makeTimeComparison() {
        try {
            System.out.println("Zeitvergleich getCountryWithMostTraffic");

            long startTime = System.currentTimeMillis();
            statisticAPI.getCountryWithMostTraffic();
            long endTime = System.currentTimeMillis();
            long countryWithMostTrafficTime = endTime - startTime;

            startTime = System.currentTimeMillis();
            statisticAPI.getCountryWithMostTrafficJAVA();
            endTime = System.currentTimeMillis();
            long countryWithMostTrafficJavaTime = endTime - startTime;

            System.out.println("Criteria Api: " + countryWithMostTrafficTime + "ms");
            System.out.println("Java: " + countryWithMostTrafficJavaTime + "ms");
            System.out.println("Differenz: " + (countryWithMostTrafficJavaTime - countryWithMostTrafficTime) + "ms");
            System.out.println();

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
