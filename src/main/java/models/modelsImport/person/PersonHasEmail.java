package models.modelsImport.person;

import models.modelsImport.Table;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class PersonHasEmail implements Table {
    private String pID;
    private String email;


    public PersonHasEmail(String[] csv) {
        this.pID = csv[0];
        this.email = csv[1].replace("'", " ");
    }

    public String getImportString() {
        return "INSERT INTO social_network.hasEmail(" +
                "pID, email)" +
                "VALUES (" +
                pID + ",'" + email + "')";
    }
}


//    pID BIGINT NOT NULL,
//    email VARCHAR(255),