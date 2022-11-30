package models.modelsImport.message;

import models.modelsImport.Table;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class CommentHasTag implements Table {
    private String coID;
    private String tID;


    public CommentHasTag(String[] csv) {
        this.coID = csv[0];
        this.tID = csv[1];
    }

    public String getImportString() {
        return "INSERT INTO social_network.commenthastag(" +
                "coID, tID)" +
                "VALUES (" +
                coID + "," + tID + ")";
    }
}

