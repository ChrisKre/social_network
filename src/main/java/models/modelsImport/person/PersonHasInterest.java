package models.modelsImport.person;

import models.modelsImport.Table;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class PersonHasInterest implements Table {
    private String pID;
    private String tID;


    public PersonHasInterest(String[] csv) {
        this.pID = csv[0];
        this.tID = csv[1];
    }

    public String getImportString() {
        return "INSERT INTO social_network.personhasinterest(" +
                "pID, tID)" +
                "VALUES (" +
                pID + "," + tID + ")";
    }
}

