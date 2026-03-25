package net.enndee.journalapp.controller;

import net.enndee.journalapp.api.response.ChuckNorrisResponse;
import net.enndee.journalapp.entity.User;
import net.enndee.journalapp.service.ChuckNorrisService;
import net.enndee.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChuckNorrisService chuckNorrisService;

    @GetMapping("/health-check")
    public ResponseEntity<?> healthCheck() {
        return new ResponseEntity<>("health-ok", HttpStatus.OK);
    }

    @GetMapping("/Norris")
    public ResponseEntity<String> getNorrisController() {

        String msg = chuckNorrisService.getNorris().getValue();
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PostMapping("/create-user")
    public void createUser(@RequestBody User user) {
        userService.saveNewUser(user);
//        userService.saveUser(user);
    }
}
