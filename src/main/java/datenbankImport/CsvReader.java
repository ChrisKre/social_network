package datenbankImport;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import models.modelsImport.Table;
import models.modelsImport.forum.Forum;
import models.modelsImport.forum.ForumHasMember;
import models.modelsImport.forum.ForumHasTag;
import models.modelsImport.message.Comment;
import models.modelsImport.message.CommentHasTag;
import models.modelsImport.message.Post;
import models.modelsImport.message.PostHasTag;
import models.modelsImport.organisation.OrganisationImport;
import models.modelsImport.person.*;
import models.modelsImport.place.PlaceImport;
import models.modelsImport.tag.Tag;
import models.modelsImport.tag.TagClass;
import models.modelsImport.tag.TagHasType;
import models.modelsImport.tag.TagIsSubclassOf;
import org.apache.ibatis.javassist.tools.rmi.ObjectNotFoundException;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;
import java.sql.*;
import java.util.List;
import java.util.Stack;


public class CsvReader {
    private static String driverClassName;
    private static String username;
    private static String password;
    private static String url; // spezifiziert JDBC-Treiber, Verbindungsdaten

    public CsvReader(String driverClassName, String username, String password, String url) {
        this.driverClassName = driverClassName;
        this.username = username;
        this.password = password;
        this.url = url;
    }

    public static void loadDriver() {
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void execSQLFile(String filename) throws FileNotFoundException, SQLException {
        Connection con = DriverManager.getConnection(url, username, password);
        System.out.println("Connection established......");
        //Initialize the script runner
        ScriptRunner sr = new ScriptRunner(con);
        //Creating a reader object
        Reader reader = new BufferedReader(new FileReader(filename));
        //Running the script
        sr.runScript(reader);
    }

    public List<String[]> getCsvData(final String pathToCsv) throws IOException {
        FileReader filereader = new FileReader(pathToCsv);
        CSVParser parser = new CSVParserBuilder()
                .withSeparator('|')
                .withFieldAsNull(CSVReaderNullFieldIndicator.BOTH)
                .build();
        // create csvReader object and skip first Line
        CSVReader csvReader = new CSVReaderBuilder(filereader)
                .withSkipLines(1)
                .withCSVParser(parser)
                .build();
        List<String[]> allData = csvReader.readAll();
        return allData;
    }

    public String getClass(String filename) {
        String[] split = filename.split("_0_0.csv");
        String split1 = split[split.length - 1];
        String[] split2 = split1.split("/");
        return split2[split2.length - 1];
    }

    public void importCsvToPGAdmin(final String filename) {
        try {
            final String className = getClass(filename);
            List<String[]> data = getCsvData(filename);
            if (className.equals("place")) {
                data = orderPlaceCsv(data);
            }
            Connection con = DriverManager.getConnection(url, username, password);
            Table row = null;


            PreparedStatement batchStmt = null;
            Statement statement = con.createStatement();
            for (int i = 0; i < data.size(); i++) {
                switch (className) {
                    case "person":
                        row = new Person(data.get(i));
                        break;
                    case "place":
                        row = new PlaceImport(data.get(i));
                        statement = ((PlaceImport) row).addBatch(statement);
                        break;
                    case "organisation":
                        row = new OrganisationImport(data.get(i));
                        statement = ((OrganisationImport) row).addBatch(statement);
                        break;
                    case "person_workAt_organisation":
                        row = new PersonWorkAt(data.get(i));
                        break;
                    case "person_studyAt_organisation":
                        row = new PersonStudyAt(data.get(i));
                        break;
                    case "person_knows_person":
                        row = new PersonKnowsPerson(data.get(i));
                        break;
                    case "person_email_emailaddress":
                        row = new PersonHasEmail(data.get(i));
                        break;
                    case "forum":
                        row = new Forum(data.get(i));
                        break;
                    case "forum_hasMember_person":
                        row = new ForumHasMember(data.get(i));
                        break;
                    case "forum_hasTag_tag":
                        row = new ForumHasTag(data.get(i));
                        break;
                    case "post":
                        row = new Post(data.get(i));
                        break;
                    case "post_hasTag_tag":
                        row = new PostHasTag(data.get(i));
                        break;
                    case "person_hasInterest_tag":
                        row = new PersonHasInterest(data.get(i));
                        break;
                    case "person_likes_post":
                        row = new PersonLikesPost(data.get(i));
                        break;
                    case "person_likes_comment":
                        row = new PersonLikesComment(data.get(i));
                        break;
                    case "person_speaks_language":
                        row = new PersonSpeaks(data.get(i));
                        break;
                    case "comment":
                        row = new Comment(data.get(i));
                        break;
                    case "comment_hasTag_tag":
                        row = new CommentHasTag(data.get(i));
                        break;
                    case "tag":
                        row = new Tag(data.get(i));
                        break;
                    case "tagclass":
                        row = new TagClass(data.get(i));
                        break;
                    case "tag_hasType_tagclass":
                        row = new TagHasType(data.get(i));
                        break;
                    case "tagclass_isSubclassOf_tagclass":
                        row = new TagIsSubclassOf(data.get(i));
                        break;
                    default:
                        throw new ObjectNotFoundException("Csv konnte nicht gelesen werden");
                }
                if (!className.equals("place") && !className.equals("organisation")) {
                    statement.addBatch(row.getImportString());
                }
            }
            int[] updateCounts = statement.executeBatch();
            System.out.println(updateCounts.length + " Rows von " + className + " importiert");
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    private List<String[]> orderPlaceCsv(List<String[]> data) {
        return putContinentFirst(putCountryFirst(data));
    }

    private List<String[]> putCountryFirst(List<String[]> data) {
        Stack<Integer> indicesContintents = new Stack();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i)[3].equals("country")) {
                indicesContintents.push(i);
            }
        }
        for (Integer i : indicesContintents
        ) {
            data.add(0, data.get(i));
            data.remove(i + 1);
        }
        return data;
    }

    private List<String[]> putContinentFirst(List<String[]> data) {
        Stack<Integer> indicesContintents = new Stack();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i)[3].equals("continent")) {
                indicesContintents.push(i);
            }
        }
        for (Integer i : indicesContintents
        ) {
            data.add(0, data.get(i));
            data.remove(i + 1);
        }
        return data;
    }


}


