package models.modelsImport.person;

import models.modelsImport.Table;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class PersonLikesPost implements Table {
    private String pID;
    private String poID;
    private String creationDate;


    public PersonLikesPost(String[] csv) {
        this.pID = csv[0];
        this.poID = csv[1];
        this.creationDate = csv[2];
    }

    public String getImportString() {
        return "INSERT INTO social_network.personlikespost(" +
                "pID, poID, creationDate)" +
                "VALUES (" +
                pID + "," + poID + ",'" + creationDate + "')";
    }
}

