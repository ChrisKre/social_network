package models.modelsImport.person;

import models.modelsImport.Table;
import lombok.AllArgsConstructor;

import java.sql.PreparedStatement;

@AllArgsConstructor
public class Person implements Table {
    private String pid;
    private String firstName;
    private String lastName;
    private String gender;
    private String birthday;
    private String creationDate;
    private String locationIP;
    private String browserUsed;
    private String isLocatedIn;

    public Person(String[] csv){
        this.pid = csv[0];
        this.firstName = csv[1];
        this.lastName = csv[2];
        this.gender = csv[3];
        this.birthday = csv[4];
        this.creationDate = csv[5];
        this.locationIP = csv[6];
        this.browserUsed = csv[7];
        this.isLocatedIn = csv[8];
    }

    public String getImportString() {
        return "INSERT INTO social_network.person(" +
                "pid, firstName, lastName, gender, birthday, " +
                "creationDate, locationIp, browserUsed, isLocatedIn)" +
                "VALUES (" +
                pid + ",'" + firstName + "','" + lastName + "','" + gender + "','" + birthday + "','" +
                creationDate + "','" + locationIP + "','" + browserUsed + "'," + isLocatedIn + ")";
    }

}


