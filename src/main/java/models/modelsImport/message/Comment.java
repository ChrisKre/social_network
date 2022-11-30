package models.modelsImport.message;

import models.modelsImport.Table;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Comment implements Table, Message {
    private String coID;
    private String creationDate;
    private String locationIP;
    private String browserUsed;
    private String content;
    private String length;
    private String hasCreator;
    private String plID;
    private String replyOfPost;
    private String replyOfComment;


    public Comment(String[] csv) {
        this.coID = csv[0];
        this.creationDate = csv[1];
        this.locationIP = csv[2];
        this.browserUsed = csv[3];
        this.content = (csv[4] == null) ? null : csv[6].replace("'", " ");
        this.length = csv[5];
        this.hasCreator = csv[6];
        this.plID = csv[7];
        this.replyOfPost = csv[8];
        this.replyOfComment = csv[9];
    }

    public String getImportString() {
        return "INSERT INTO social_network.comment_(" +
                "coID, creationDate, locationIP, browserUsed, content, " +
                "length, hasCreator, plID, replyOfPost, replyOfComment)" +
                "VALUES (" +
                coID + ",'" + creationDate + "','" + locationIP + "','" + browserUsed + "','" + content + "'," +
                length + "," + hasCreator + "," + plID + "," + replyOfPost + "," + replyOfComment+")";
    }
}



