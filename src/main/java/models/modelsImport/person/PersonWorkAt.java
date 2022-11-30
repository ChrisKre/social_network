package models.modelsImport.person;

import models.modelsImport.Table;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PersonWorkAt implements Table {
    private String pid;
    private String oid;
    private String workfrom;

    public PersonWorkAt(String[] csv) {
        this.pid = csv[0];
        this.oid = csv[1];
        this.workfrom = csv[2].replace("'", "_");
    }

    @Override
    public String getImportString() {
        return "INSERT INTO social_network.workat(" +
                "pid, oid, workfrom)" +
                "VALUES (" + pid + ", " + oid + ", " + workfrom + ")";
    }
}