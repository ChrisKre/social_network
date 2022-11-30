package models.modelsImport.forum;

import models.modelsImport.Table;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class ForumHasTag implements Table {
    private String fID;
    private String tID;


    public ForumHasTag(String[] csv) {
        this.fID = csv[0];
        this.tID = csv[1];
    }

    public String getImportString() {
        return "INSERT INTO social_network.hasTag(" +
                "fID, tID)" +
                "VALUES (" +
                fID + "," + tID + ")";
    }
}

