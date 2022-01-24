package com.example.gradlebnbadminapi.service;

import com.example.gradlebnbadminapi.exception.EmailNotExistedException;
import com.example.gradlebnbadminapi.jwt.TokenProvider;
import com.example.gradlebnbadminapi.model.entity.User;
import com.example.gradlebnbadminapi.model.network.Header;
import com.example.gradlebnbadminapi.model.network.request.SessionApiRequest;
import com.example.gradlebnbadminapi.model.network.response.SessionApiResponse;
import com.example.gradlebnbadminapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.time.LocalDateTime;

@Slf4j
@Service
public class SessionApiLogicServiceImpl implements SessionApiLogicService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserApiLogicServiceImpl userApiLogicServiceImpl;

    @Override
    public Header<SessionApiResponse> authenticate(SessionApiRequest request) throws Exception {

        String email = request.getEmail();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new EmailNotExistedException(email));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.error("Wrong Password");
            throw new LoginException("login Error");
        }

        userRepository.save(user.setLastLoginAt(LocalDateTime.now()));

        return Header.OK(response(user));
    }

    public SessionApiResponse response(User user) {
        return SessionApiResponse.builder()
                .accessToken(tokenProvider.createToken(user))
                .userApiResponse(userApiLogicServiceImpl.response(user))
                .build();
    }
}
