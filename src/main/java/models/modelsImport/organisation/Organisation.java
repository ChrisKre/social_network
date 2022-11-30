package models.modelsImport.organisation;

import models.modelsImport.Table;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Organisation implements Table {
    private String oid;
    private String name;

    @Override
    public String getImportString() {
        return "INSERT INTO social_network.organisation(" + "oid, name)" +
                "VALUES (" + oid + ",'" + name + "');";
    }
}
