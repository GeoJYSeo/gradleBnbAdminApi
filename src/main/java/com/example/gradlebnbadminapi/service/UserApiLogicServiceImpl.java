package com.example.gradlebnbadminapi.service;

import com.example.gradlebnbadminapi.exception.EmailDuplicatedException;
import com.example.gradlebnbadminapi.exception.EmailNotExistedException;
import com.example.gradlebnbadminapi.jwt.TokenProvider;
import com.example.gradlebnbadminapi.model.entity.User;
import com.example.gradlebnbadminapi.model.enumClass.UserAccess;
import com.example.gradlebnbadminapi.model.enumClass.UserStatus;
import com.example.gradlebnbadminapi.model.network.Header;
import com.example.gradlebnbadminapi.model.network.request.UserApiRequest;
import com.example.gradlebnbadminapi.model.network.response.UserApiResponse;
import com.example.gradlebnbadminapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
public class UserApiLogicServiceImpl implements UserApiLogicService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Header<UserApiResponse> create(Header<UserApiRequest> request) throws Exception {

        UserApiRequest userApiRequest = request.getData();
        System.out.println(userApiRequest);
        String email = userApiRequest.getEmail();

        if (hasEmail(email)) {
            log.error("Email duplicated");
            throw new EmailDuplicatedException(email);
        } else {
            String encodedPassword = passwordEncoder.encode(userApiRequest.getPassword());

            User user = User.builder()
                    .email(userApiRequest.getEmail())
                    .password(encodedPassword)
                    .firstname(userApiRequest.getFirstname())
                    .lastname(userApiRequest.getLastname())
                    .birthday(userApiRequest.getBirthday())
                    .status(UserStatus.ACTIVATED)
                    .access(UserAccess.MANAGER)
                    .createdAt(LocalDateTime.now())
                    .build();

            userRepository.save(user);

            log.info("User Created:" + user.toString());

            return Header.OK();
        }
    }

    @Override
    public Header<UserApiResponse> read(String email) throws Exception {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EmailNotExistedException(email));

        return Header.OK(response(user));
    }

    public UserApiResponse response(User user) {
        return UserApiResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .birthday(user.getBirthday())
                .access(user.getAccess().getTitle())
                .status(user.getStatus().getTitle())
                .build();
    }

    private boolean hasEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
