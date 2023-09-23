//package com.FinalProject.security.service;
//
//import com.FinalProject.security.exception.UserNotFound;
//import com.FinalProject.security.model.User;
//import com.FinalProject.security.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class UserService {
//
//    private final UserRepository userRepository;
//
//    public String getUserImage(User user) {
//        User user1 = userRepository.findUserByIsActive(user.getIsActive()).orElseThrow(
//                () -> new UserNotFound("Active user not found")
//        );
//
//        return user1.getImage();
//    }
//
//    public String getUserCredentials(User user) {
//        User user1 = userRepository.findUserByIsActive(user.getIsActive()).orElseThrow(
//                () -> new UserNotFound("Active user not found")
//        );
//        return user1.getFirstname().concat(" ") + user1.getLastname();
//    }
//
//    public Set<User> findAllActiveUsers() {
//        return userRepository.findAll().stream().filter
//                (
//                        user -> user.getIsActive() == Boolean.TRUE
//                ).collect(Collectors.toSet());
//    }
//
//
//}
