package models.modelsImport.person;

import models.modelsImport.Table;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class PersonSpeaks implements Table {
    private String pID;
    private String lng;


    public PersonSpeaks(String[] csv) {
        this.pID = csv[0];
        this.lng = csv[1];
    }

    public String getImportString() {
        return "INSERT INTO social_network.speaks(" +
                "pID, lng)" +
                "VALUES (" +
                pID + ",'" + lng + "')";
    }
}

