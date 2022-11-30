package models.modelsImport.organisation;

import lombok.AllArgsConstructor;
import models.modelsImport.Table;
import org.apache.ibatis.javassist.tools.rmi.ObjectNotFoundException;

import java.sql.SQLException;
import java.sql.Statement;

@AllArgsConstructor
public class OrganisationImport implements Table {
    private Organisation organisation;
    private Company company;
    private University university;
    private String oid;
    private String organisationtype;
    private String name;
    private String islocatedin;

    public OrganisationImport(String[] csv) throws ObjectNotFoundException {
        oid = csv[0];
        organisationtype = csv[1];
        name = csv[2].replace("'", "_");
        islocatedin = csv[4];

        if (organisationtype.equals("company")) {
            company = new Company(oid, name, islocatedin);
        } else if (organisationtype.equals("university")) {
            university = new University(oid, name, islocatedin);
        } else {
            throw new ObjectNotFoundException("Beim Placeimport ist ein Fehler augetreten!!!");
        }
    }

    public Statement addBatch(Statement statement) throws SQLException {
        if (organisationtype.equals("company")) {
            statement.addBatch(company.getImportString());
        } else if (organisationtype.equals("university")) {
            statement.addBatch(university.getImportString());
        }
        return statement;
    }

    @Override
    public String getImportString() {
        if (organisationtype.equals("company")) {
            return organisation.getImportString() + company.getImportString();
        } else if (organisationtype.equals("university")) {
            return organisation.getImportString() + university.getImportString();
        } else {
            return "";
        }
    }
}
