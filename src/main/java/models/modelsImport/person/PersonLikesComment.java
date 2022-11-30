package models.modelsImport.person;

import models.modelsImport.Table;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class PersonLikesComment implements Table {
    private String pID;
    private String coID;
    private String creationDate;


    public PersonLikesComment(String[] csv) {
        this.pID = csv[0];
        this.coID = csv[1];
        this.creationDate = csv[2];
    }

    public String getImportString() {
        return "INSERT INTO social_network.personlikescomment(" +
                "pID, coID, creationDate)" +
                "VALUES (" +
                pID + "," + coID + ",'" + creationDate + "')";
    }
}

