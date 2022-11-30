package models.modelsImport.tag;

import models.modelsImport.Table;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TagClass implements Table {
    String tcID;
    String name;

    public TagClass(String[] csv) {
        this.tcID = csv[0];
        this.name = csv[1].replace("'", " ");

    }

    @Override
    public String getImportString() {
        return "INSERT INTO social_network.tagclass(" +
                "tcID, name)" +
                "VALUES (" +
                tcID + ",'" + name + "')";
    }
}

