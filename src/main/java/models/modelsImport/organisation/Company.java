package models.modelsImport.organisation;

import lombok.AllArgsConstructor;
import models.modelsImport.Table;

@AllArgsConstructor
public class Company implements Table {
    private String oid;
    private String name;
    private String islocatedin;

    @Override
    public String getImportString() {
        return "INSERT INTO social_network.company(" + "oid, name, islocatedin)" +
                "VALUES (" + oid + ",'" + name + "','" + islocatedin + "');";
    }
}
