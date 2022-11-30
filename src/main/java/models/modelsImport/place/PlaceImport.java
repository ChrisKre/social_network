package models.modelsImport.place;

import models.modelsImport.Table;
import lombok.AllArgsConstructor;
import org.apache.ibatis.javassist.tools.rmi.ObjectNotFoundException;

import java.sql.SQLException;
import java.sql.Statement;

@AllArgsConstructor
public class PlaceImport implements Table {
    private Place place;
    private Continent continent;
    private Country country;
    private City city;
    private String plID;
    private String name;
    private String placetype;
    private String isPartOf;

    public PlaceImport(String[] csv) throws ObjectNotFoundException {
        plID = csv[0];
        name = csv[1].replace("'", " ");
        placetype = csv[3];
        isPartOf = csv[4];

        if(placetype.equals("continent")){
            continent = new Continent(plID, name);
        }
        else if(placetype.equals("country")){
            country = new Country(plID, isPartOf, name);
        }
        else if(placetype.equals("city")){
            city = new City(plID, isPartOf, name);
        }
        else{
            throw new ObjectNotFoundException("Beim Placeimport ist ein Fehler augetreten!!!");
        }
    }

    public Statement addBatch(Statement statement) throws SQLException {
        if (placetype.equals("continent")) {
            statement.addBatch(continent.getImportString());
        } else if (placetype.equals("country")) {
            statement.addBatch(country.getImportString());
        } else if (placetype.equals("city")) {
            statement.addBatch(city.getImportString());
        }
        return statement;
    }

    @Override
    public String getImportString() {
        if (placetype.equals("continent")) {
            return place.getImportString() + continent.getImportString();
        } else if (placetype.equals("country")) {
            return place.getImportString() + country.getImportString();
        } else if (placetype.equals("city")) {
            return place.getImportString() + city.getImportString();
        } else {
            return "";
        }
    }
}

