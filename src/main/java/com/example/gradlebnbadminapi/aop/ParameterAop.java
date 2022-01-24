package com.example.gradlebnbadminapi.aop;

import com.example.gradlebnbadminapi.exception.UnauthenticatedException;
import com.example.gradlebnbadminapi.jwt.TokenProvider;
import com.example.gradlebnbadminapi.model.entity.User;
import com.example.gradlebnbadminapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class ParameterAop {

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Pointcut("execution(* com.example.gradlebnbadminapi.controller..*.*(..)) && !@annotation(com.example.gradlebnbadminapi.annotation.NoAuth)")
    private void BeforeCut() {}

    @Before("BeforeCut()")
    public void Before(JoinPoint joinPoint) {
        Map<String, Object> claims = tokenProvider.getClaimsData(SecurityContextHolder.getContext().getAuthentication());
        User user = userRepository.findByEmail(claims.get("email").toString()).orElseThrow(UnauthenticatedException::new);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        System.out.println("-------------------------");
//        String params = (String) joinPoint.getArgs()[0].getEmail();
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        Arrays.stream(signature.getMethod().getParameters()).forEach((parameter -> System.out.println(parameter.getType())));
        System.out.println(request.getParameter("email"));
        System.out.println("-------------------------");

        String uri = request.getRequestURI().toString();
        log.info("Request Method: " + request.getMethod());
        log.info("Request URI: " + uri);

        if (Arrays.toString(uri.split("/")).contains("admin")) {
            log.info("Admin accessed");
            checkAccess((int) claims.get("access"), user.getAccess().getId());
        }
        log.info("Authenticated: " + claims.get("email").toString());
    }

    private void checkAccess(int claimsAccess, int userAccess) {

        HashSet<Integer> accessSet = new HashSet<>();
        accessSet.add(claimsAccess);
        accessSet.add(userAccess);

        if (!accessSet.contains(9)) {
            log.error("Authenticated Error");
            throw new UnauthenticatedException();
        }
    }
}
