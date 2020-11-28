package com.example.icmproject.commonDataModel;

//Used only has a holder for an ID plus an email for client
public class UserView {
    private  String username;
    private String dbId;

    public UserView(String username, String dbId) {
        this.username = username;
        this.dbId = dbId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }
}
