package models.modelsImport.organisation;

import models.modelsImport.Table;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class University implements Table {
    private String oid;
    private String name;
    private String islocatedin;

    @Override
    public String getImportString() {
        return "INSERT INTO social_network.university(" + "oid, name, islocatedin)" +
                "VALUES (" + oid + ",'" + name + "','" + islocatedin + "');";
    }
}
