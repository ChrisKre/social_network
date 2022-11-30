package models.modelsImport.tag;

import models.modelsImport.Table;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TagIsSubclassOf implements Table {
    String tcID;
    String isSubClassOf;

    public TagIsSubclassOf(String[] csv) {
        this.tcID = csv[0];
        this.isSubClassOf = csv[1];

    }

    @Override
    public String getImportString() {
        return "INSERT INTO social_network.tagissubclassof(" +
                "tcID, isSubClassOf)" +
                "VALUES (" +
                tcID + ",'" + isSubClassOf + "')";
    }
}

