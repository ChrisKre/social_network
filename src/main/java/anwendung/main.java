package anwendung;

import anwendung.controller.PersonenApiController;
import anwendung.controller.StatistikController;

import static utils.api.Helper.getInput;

public class main {
    private static PersonenApiController personenApiController;
    private static StatistikController statistikController;

    public static void printMöglichkeiten() {
        System.out.println("Welche Api möchten Sie benutzen?");
        System.out.println("(1) PersonenApi?");
        System.out.println("(2) StatistikApi?");
        System.out.println("(x) Programm beenden?");
    }

    public static void main(String[] args) {
        String input = null;
        do {
            printMöglichkeiten();
            input = getInput();
            switch (input) {
                case "1":
                    personenApiController = new PersonenApiController();
                    personenApiController.run();
                    break;
                case "2":
                    statistikController = new StatistikController();
                    statistikController.run();
                    break;
            }
        } while (!input.equals("x"));
    }
}
