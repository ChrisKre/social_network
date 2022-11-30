package models.modelsImport.forum;

import models.modelsImport.Table;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class ForumHasMember implements Table {
    private String fID;
    private String pID;
    private String joinDate;


    public ForumHasMember(String[] csv) {
        this.fID = csv[0];
        this.pID = csv[1];
        this.joinDate = csv[2];
    }

    public String getImportString() {
        return "INSERT INTO social_network.hasMember(" +
                "fID, pID, joinDate)" +
                "VALUES (" +
                fID + "," + pID + ",'" + joinDate + "')";
    }
}

