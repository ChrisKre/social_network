package models.modelsImport.tag;

import models.modelsImport.Table;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Tag implements Table {
    String tID;
    String name;

    public Tag(String[] csv) {
        this.tID = csv[0];
        this.name = csv[1].replace("'", " ");

    }

    @Override
    public String getImportString() {
        return "INSERT INTO social_network.tag(" +
                "tID, name)" +
                "VALUES (" +
                tID + ",'" + name + "')";
    }
}

