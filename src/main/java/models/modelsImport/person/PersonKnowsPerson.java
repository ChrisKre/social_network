package models.modelsImport.person;

import models.modelsImport.Table;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PersonKnowsPerson implements Table {
    private String pid;
    private String knows;
    private String creationDate;

    public PersonKnowsPerson(String[] csv){
        this.pid = csv[0];
        this.knows = csv[1];
        this.creationDate = csv[2];
    }

    public String getImportString() {
        return "INSERT INTO social_network.knows(" +
                "pid, knows, creationDate)" +
                "VALUES (" +
                pid + "," + knows + ",'" + creationDate + "')";
    }
}


