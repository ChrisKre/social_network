package models.modelsImport.forum;

import models.modelsImport.Table;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class Forum implements Table {
    private String fID;
    private String title;
    private String creationDate;
    private String hasModerator;


    public Forum(String[] csv){
        this.fID = csv[0];
        this.title = csv[1];
        this.creationDate = csv[2].replace("'", "_");
        this.hasModerator = csv[3];
    }

    public String getImportString() {
        return "INSERT INTO social_network.forum(" +
                "fID, title, creationDate, hasModerator)" +
                "VALUES (" +
                fID + ",'" + title + "','" + creationDate + "', " + hasModerator + ")";
    }
}

