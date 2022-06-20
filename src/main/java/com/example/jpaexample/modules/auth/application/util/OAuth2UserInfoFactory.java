package com.example.jpaexample.modules.auth.application.util;

import com.example.jpaexample.modules.auth.application.exception.OAuth2AuthenticationProcessingException;
import com.example.jpaexample.modules.auth.web.dto.oauth2.GoogleOAuth2UserInfo;
import com.example.jpaexample.modules.auth.web.dto.oauth2.OAuth2UserInfo;
import com.example.jpaexample.modules.user.domain.enums.AuthProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
//        } else if (registrationId.equalsIgnoreCase(AuthProvider.facebook.toString())) {
//            return new FacebookOAuth2UserInfo(attributes);
//        } else if (registrationId.equalsIgnoreCase(AuthProvider.github.toString())) {
//            return new GithubOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
