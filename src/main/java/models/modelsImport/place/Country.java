package models.modelsImport.place;

import models.modelsImport.Table;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Country implements Table {
    String plID;
    String isPartOf;
    String name;

    @Override
    public String getImportString() {
        return "INSERT INTO social_network.country(" +
                "plid, name, ispartof)" + "VALUES (" + plID + ", '" + name  + "', " +isPartOf + ");";
    }
}
