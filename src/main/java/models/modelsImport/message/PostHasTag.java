package models.modelsImport.message;

import models.modelsImport.Table;

public class PostHasTag implements Table {
    private String poID;
    private String tID;


    public PostHasTag(String[] csv) {
        this.poID = csv[0];
        this.tID = csv[1];
    }

    public String getImportString() {
        return "INSERT INTO social_network.posthastag(" +
                "poID, tID)" +
                "VALUES (" +
                poID + "," + tID + ")";
    }
}
