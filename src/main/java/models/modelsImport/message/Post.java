package models.modelsImport.message;

import models.modelsImport.Table;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Post implements Table, Message {
    private String poID;
    private String ImageFile;
    private String creationDate;
    private String locationIP;
    private String browserUsed;
    private String language;
    private String content;
    private String length;
    private String hasCreator;
    private String fID;
    private String plID;

    public Post(String[] csv) {
        this.poID = csv[0];
        this.ImageFile = csv[1];
        this.creationDate = csv[2];
        this.locationIP = csv[3];
        this.browserUsed = csv[4];
        this.language = csv[5];
        this.content = (csv[6] == null) ? null : csv[6].replace("'", " ");
        this.length = csv[7];
        this.hasCreator = csv[8];
        this.fID = csv[9];
        this.plID = csv[10];
    }

    public String getImportString() {
        return "INSERT INTO social_network.post(" +
                "poID, ImageFile, creationDate, locationIP, browserUsed, " +
                "language, content, length, hasCreator, fID, plID)" +
                "VALUES (" +
                poID + ",'" + ImageFile + "','" + creationDate + "','" + locationIP + "','" + browserUsed + "','" +
                language + "','" + content + "'," + length + "," + hasCreator + "," + fID + "," + plID + ")";
    }
}

