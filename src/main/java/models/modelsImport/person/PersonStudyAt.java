package models.modelsImport.person;

import models.modelsImport.Table;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PersonStudyAt implements Table {
    private String pid;
    private String oid;
    private String classYear;

    public PersonStudyAt(String[] csv) {
        this.pid = csv[0];
        this.oid = csv[1];
        this.classYear = csv[2];
    }

    @Override
    public String getImportString() {
        return "INSERT INTO social_network.studyat(" +
                "pid, oid, classYear)" +
                "VALUES (" + pid + ", " + oid + ", " + 	classYear + ")";
    }
}