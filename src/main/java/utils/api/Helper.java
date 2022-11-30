package utils.api;

import models.modelsHibernate.person.Person;

import java.util.Scanner;
import java.util.Set;

public class Helper {

    public static String getInput() {
        Scanner myObj = new Scanner(System.in);
        return myObj.nextLine();
    }

    public static void printSetPersonShort(Set<Person> set) {
        printEmpty(set);
        for (Person t : set) {
            System.out.println(t.toStringShort());
        }
    }

    public static <T> void printSet(Set<T> set) {
        printEmpty(set);
        for (T t : set) {
            System.out.println(t);
        }
    }

    public static <T> void printEmpty(Set<T> set) {
        if (set.size() == 0) {
            System.out.println("Für diese Anfrage konnten leider keine Ergebnisse generiert werden");
        }
    }
}
