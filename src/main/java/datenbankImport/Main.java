package datenbankImport;

import utils.Csvs;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class Main {
    private static String csvDir = "C:\\Users\\Christopher\\Documents\\dbae\\social_network";
    private static String sqlInit = "src/main/SqlSkripte/init.sql";
    private static String driverClassName = "org.postgresql.Driver";
    private static String username = "stud04";
    private static String password = "9cR8r/9L";
    private static String url = "jdbc:postgresql://pgsql.ins.hs-anhalt.de:5432/stud04_db"; // spezifiziert JDBC-Treiber, Verbindungsdaten

    static CsvReader csvReader = new CsvReader(
            driverClassName,
            username,
            password,
            url);

    public static void main(String[] args) throws FileNotFoundException, SQLException {
        CsvReader.loadDriver();
        CsvReader.execSQLFile(sqlInit);
        Csvs csvs = new Csvs(csvDir);
        for (String csv : csvs.getUrls()) {
            csvReader.importCsvToPGAdmin(csv);
        }
    }
}
