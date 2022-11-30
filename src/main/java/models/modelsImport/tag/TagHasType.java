package models.modelsImport.tag;

import models.modelsImport.Table;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TagHasType implements Table {
    String tID;
    String tcID;

    public TagHasType(String[] csv) {
        this.tID = csv[0];
        this.tcID = csv[1];
    }

    @Override
    public String getImportString() {
        return "INSERT INTO social_network.TagHasType(" +
                "tID, tcID)" +
                "VALUES (" +
                tID + ", " + tcID + ")";
    }
}

