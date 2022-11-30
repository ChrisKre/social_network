package models.modelsImport.place;

import lombok.AllArgsConstructor;
import models.modelsImport.Table;

@AllArgsConstructor
public class Continent implements Table {
    String plID;
    String name;

    @Override
    public String getImportString() {
        return "INSERT INTO social_network.continent(" +
                "plid, name)" + "VALUES (" + plID + ", '" + name + "');";
    }
}
