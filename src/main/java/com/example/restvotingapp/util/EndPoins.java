package com.example.restvotingapp.util;

public class EndPoins {

    public static final String USERS    = "/users";
    public static final String USERS_ID = "/users/{id}";

    public static final String RESTAURANTS        = "/restaurants";
    public static final String RESTAURANTS_ID     = "/restaurants/{id}";
    public static final String RESTAURANTS_ACTIVE = "/restaurants/active";

    public static final String MENUS                 = "/menus";
    public static final String MENUS_ID              = "/menus/{id}";
    public static final String MENUS_DATE            = "/menus/date/{date}";
    public static final String MENUS_DATE_RESTAURANT = "/menus/date/{date}/restaurant/{restaurant_id}";

    public static final String VOTES                       = "/votes";
    public static final String VOTES_ID                    = "/votes/{id}";
    public static final String VOTES_DATE                  = "/votes/date/{date}";
    public static final String VOTES_DATE_COUNT            = "/votes/date/{date}/count";
    public static final String VOTES_DATE_COUNT_RESTAURANT = "/votes/date/{date}/count/restaurant/{restaurant_id}";

}
