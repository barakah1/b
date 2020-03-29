package com.example.barakah.utils;

public class BarakahConstants {
    public static final String HOME_ACTIVITY = "HOME_ACTIVITY";
    public static final String HEALTH_STATUS_FM = "HEALTH_STATUS_FM";
    public static final String HEALTH_STATUS_DATA = "HEALTH_STATUS_DATA";
    public static final int GET_HEALTH_STATUS = 600;
    public static String EMAIL = "EMAIL";
    public static String HERBS_MODEL = "HERBS_MODEL";
    public static String HERBS_DETAILS = "HERBS_DETAILS";
    public static  String quantity = "quantity";
    public static  String SELECT_HERBS_VENDOR = "SELECT_HERBS_VENDOR";
    public static  String CART_DATA = "CART_DATA";
    public static String DELIVERED="2";
    public static String ORDER_DETAILS="ORDER_DETAILS";
    public static String ORDER_MODEL="ORDER_MODEL";

    public interface DbTABLE {
        String CART = "CART";
        String ORDERS = "ORDERS";
        String STOREITEM = "STOREITEM";
        String CUSTOMER = "CUSTOMER";
        String HERB = "HERB";
        String FAVOURITE = "FAVOURITE";
        String CUSTOMER_HEALTH_STATUS = "CUSTOMER_HEALTH_STATUS";
        String MEDICAL_HISTORY = "MEDICAL_HISTORY";

    }

    public interface USER_PREF {
        String IS_LOGEDIN = "IS_LOGEDIN";
        String CUSTOMER = "CUSTOMER";
        String CUSTOMER_HEALTH_STATUS = "CUSTOMER_HEALTH_STATUS";

    }

}
