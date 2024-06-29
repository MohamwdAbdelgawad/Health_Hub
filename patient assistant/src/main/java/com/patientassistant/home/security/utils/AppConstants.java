package com.patientassistant.home.security.utils;

public class AppConstants {
    public static final String AUTH_HEADER = "Authentication";
    public static final int ACCESS_EXPATRIATION_TIME = 60 * 60 * 1000;
    public static final int REFRESH_EXPATRIATION_TIME = 7 * ACCESS_EXPATRIATION_TIME;

    public static final String CLIENT_URL = "https://healthhub-production-5029.up.railway.app";

    public static final String EMAIL_CONFORMATION_PREFIX = CLIENT_URL + "/auth/confirm?token=";

    public static final String LOCAL_PROVIDER_ID = "412429";
}
