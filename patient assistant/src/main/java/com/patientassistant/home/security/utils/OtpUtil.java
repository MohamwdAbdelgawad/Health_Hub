package com.patientassistant.home.security.utils;

import java.util.Random;

public class OtpUtil {
    public static String generateOtp() {
        return String.valueOf(new Random().nextInt(999999));
    }
}
