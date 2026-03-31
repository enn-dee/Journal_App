package net.enndee.journalapp.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.enndee.journalapp.api.response.ChuckNorrisResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Component
@Getter
@Setter
public class ChuckNorrisService {

//   ====== get api value from applicaton.properties ====
//    @Value("${chuck.norris.api}")
//    private String API;


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;

    enum allowedCategories {
        ANIMAL,
        CAREER,
        CELEBRITY,
        DEV,
        EXPLICIT,
        FASHION,
        FOOD,
        HISTORY,
        MONEY,
        MOVIE,
        MUSIC,
        POLITICAL,
        RELIGION,
        SCIENCE,
        SPORT,
        TRAVEL
    }

    public ChuckNorrisResponse getNorris(String category) {
//     ======   testing post req =====
//        User user = (User) org.springframework.security.core.userdetails.User.builder().username("nadeem").password("12345").build();
//        User user = new User("Nadeem", "12345");
//        HttpHeaders headers = new HttpHeaders();
//
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<User>  rqst = new HttpEntity<>(user,headers);
//        ResponseEntity<String> response = restTemplate.exchange(
//                "http://localhost:8080/public/create-user",
//                HttpMethod.POST,
//                rqst,
//                String.class
//        );

        try {
            ChuckNorrisResponse chuckNorrisResponse = redisService.get("norris_" + category, ChuckNorrisResponse.class);
            if (chuckNorrisResponse != null) {
                return chuckNorrisResponse;
            } else {
                String API = appCache.APP_CACHE.get("api").replace("{category}", category.toLowerCase());
                ChuckNorrisResponse body = restTemplate.exchange(API, HttpMethod.GET, null, ChuckNorrisResponse.class).getBody();

                if (body != null)
                    redisService.set("norris_" + category, body, 300l);
                return body;
            }
        } catch (Exception e) {
            log.error("Error occurred in Chuck_Norris service: " + e.getMessage());
            return null;
        }
    }

}
