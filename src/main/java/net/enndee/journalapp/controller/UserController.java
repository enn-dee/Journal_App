package net.enndee.journalapp.controller;

import net.enndee.journalapp.entity.User;
import net.enndee.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUser() {
        return userService.getAllUsers();
    }


    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();


        User userInDB = userService.findByUsername(username);

        userInDB.setUsername(user.getUsername());
        userInDB.setPassword(user.getPassword());
        userService.saveNewUser(userInDB);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        //        User isUser = userService.findByUsername(user.getUsername());
//        if (isUser != null) {
//            isUser.setUsername(user.getUsername());
//            isUser.setPassword(user.getPassword());
//            userService.saveUser(isUser);
//            return ResponseEntity.status(HttpStatus.CREATED).body("User Updated");
//        }
//        return new ResponseEntity<>("User not found", HttpStatus.NO_CONTENT);

    }
}
