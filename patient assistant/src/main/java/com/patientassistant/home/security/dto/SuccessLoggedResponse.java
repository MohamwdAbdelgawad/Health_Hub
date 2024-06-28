package com.patientassistant.home.security.dto;


import com.patientassistant.home.security.entites.User;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

import static com.patientassistant.home.security.utils.AppConstants.ACCESS_EXPATRIATION_TIME;

@Setter
@Getter
@Data
@Builder
@AllArgsConstructor
public class SuccessLoggedResponse {

    private String username;
    private long userId;
    private String accessToken;
    private String refreshToken;
    private Date expirationDate;


    public static SuccessResponseHandler getSuccessfulResponse(User user, String message, String accessToken, String refreshToken) {
        SuccessLoggedResponse sucResponse = SuccessLoggedResponse.builder()
                .username(user.getUsername())
                .userId(user.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expirationDate(new Date(System.currentTimeMillis() + ACCESS_EXPATRIATION_TIME))
                .build();
        return SuccessResponseHandler.builder()
                .body(sucResponse)
                .message(message)
                .ok(true)
                .status(HttpStatus.OK.value())
                .build();
    }

    public static SuccessResponseHandler SuccessRegisterResponse(User user, String message) {
        SuccessLoggedResponse sucResponse = SuccessLoggedResponse.builder()
                .username(user.getUsername())
                .userId(0)
                .accessToken(null)
                .refreshToken(null)
                .expirationDate(null)
                .build();
        return SuccessResponseHandler.builder()
                .body(sucResponse)
                .message(message)
                .ok(true)
                .status(HttpStatus.OK.value())
                .build();
    }
}
