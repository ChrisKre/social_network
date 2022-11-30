package models.modelsImport.place;

import models.modelsImport.Table;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Place implements Table {
    private String plID;
    private String name;

    @Override
    public String getImportString() {
        return "INSERT INTO social_network.place(" + "plid, name)" +
                "VALUES (" + plID + ",'" + name + "');";
    }
}
