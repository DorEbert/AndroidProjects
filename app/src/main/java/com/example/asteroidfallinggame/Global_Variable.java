package com.example.asteroidfallinggame;

class Global_Variable {
    public static final String MY_POINTS = "myPoints";
    public static final String CURRENT_POINTS = "Current Points";
    public static final String LOGIN = "Login";
    public static final String PASSWORD = "Password";
    public static final String USERNAME = "Username";
    public static final String WELCOME = "Welcome";
    public static final String MAX_POINTS_ACHIEVED = "Max points achieved";
    public static final String GAMES_PLAYED = "Games played";
    public static final String SCORE_SHEET = "Score Sheet";
    public static final String NEW_GAME = "new game";
    public static final String FIREICONID = "lifeHeartIcon";
    public static final int AMOUNT_OF_HORIZENTAL_LAYOUTS = 1;
    public static final int LIFECHANCES = 3;
    public static final int LIFE_DIDACTIC = 20;
    public static final int STARTED_HP = 100;
    public static final int POINTS_PER_FIREATTACK = 10;
    public static final String USERNAME_COLUMN = "username";
    public static final String APPLICATION_USER_ID = "userID";
    public static final String APPLICATION_USERS_TABLE_NAME = "ApplicationUsers";
    public static final String SIGN_UP = "Sign Up";
    public static final String MISSING_INFO_MSG = "Missing Information";
    public static final String MISSING_INFO_BODY_MSG = "Username or password are empty";
    public static final String USER_EXIST_MSG = "User already exist";
    public static final String USER_EXIST_BODY_MSG = "Choose another username";
    public static final String USER_NOT_EXIST_MSG = "User NOT exist";
    public static final String USER_NOT_EXIST_BODY_MSG = "Details are not correct";
    public static final long DURATION_OF_FIRE_ANIMATE = 5000;
    public static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    public static final String ERROR_SIGN_UP = "Error";
    public static final String ERROR_SIGN_UP_BODY_MSG = "Error signing up, Might be network problem";
    public static final String USER_LOGGED = "UsedLoggedIn";
    public static final String SCORE = "Scores";
    public static final String SCORE_SHEET_TABLE_NAME = "ScoreSheet";
    public static final String SCORE_COLUMN = "scoreAchieved";
    public static final int TOP_RESULTS = 7;
    public static final String LOCATION_COLUMN = "location";
    public static final String LATITUDE_COLUMN = "latitude";
    public static final String LONGITUDE_COLUMN = "longitude";
    public static final double CHANGE_OF_PRODUCE_FIRE_ATTACK = 0.85;
    public static final int POINT_FOR_COLLECTING_COIN = 50;
    public static final int SPEED_OF_SPACESHIPT = 50;
    public static ApplicationUserModel applicationUserModel;
    public static FireBaseUtill fireBaseUtill;
    private static int id = 1;
    public static int GetID() {
        return id++;
    }
}

