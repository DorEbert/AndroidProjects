package com.example.asteroidfallinggame;

class ApplicationUserModel {
    private String UserID;
    private String username;
    private String password;
    public static int maxPointAchieved = 0;
    public ApplicationUserModel(String id,String username, String password) {
        this.username = username;
        this.password = password;
        this.UserID = id;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getUserID(){
        return UserID;
    }

}
