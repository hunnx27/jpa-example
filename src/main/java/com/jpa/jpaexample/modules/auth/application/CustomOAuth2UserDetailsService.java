package com.jpa.jpaexample.modules.auth.application;

import com.jpa.jpaexample.core.vo.exception.OAuth2AuthenticationProcessingException;
import com.jpa.jpaexample.modules.auth.application.util.OAuth2UserInfoFactory;
import com.jpa.jpaexample.modules.auth.web.dto.oauth2.OAuth2UserInfo;
import com.jpa.jpaexample.modules.auth.web.dto.UserPrincipal;
import com.jpa.jpaexample.modules.user.domain.User;
import com.jpa.jpaexample.modules.user.domain.enums.AuthProvider;
import com.jpa.jpaexample.modules.user.domain.enums.Role;
import com.jpa.jpaexample.modules.user.infra.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Service
public class CustomOAuth2UserDetailsService extends DefaultOAuth2UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }
    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(ObjectUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
        Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        User user;
        if(userOptional.isPresent()) {
            user = userOptional.get();
            if(!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.");
            }
            //TODO 정책 결정 필요
            // 기존 유저면 덮어쓰기..
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            // 신규 유저면 등록
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }
        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }
    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {

        User user = User.builder()
                        .provider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))
                        .name(oAuth2UserInfo.getName())
                        .email(oAuth2UserInfo.getEmail())
                        .providerId(oAuth2UserInfo.getId())
                        .picture(oAuth2UserInfo.getPicture())
                        .role(Role.USER)
                .build();
        return userRepository.save(user);
    }

    // 기존 USER -> OAuth2 방식으로 덮어쓰기..
    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.update(oAuth2UserInfo.getName(), oAuth2UserInfo.getPicture());
        return userRepository.save(existingUser);
    }
}
