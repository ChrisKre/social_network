package models.modelsImport.place;

import lombok.AllArgsConstructor;
import models.modelsImport.Table;

@AllArgsConstructor
public class City implements Table {
    String plID;
    String isPartOf;
    String name;

    @Override
    public String getImportString() {
        return "INSERT INTO social_network.city(" +
                "plid, name, ispartof)" + "VALUES (" + plID + ", '" + name + "', " + isPartOf + ");";
    }
}
