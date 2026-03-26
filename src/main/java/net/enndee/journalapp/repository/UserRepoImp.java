package net.enndee.journalapp.repository;

import net.enndee.journalapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class UserRepoImp {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUsersForSA() {
        Query query = new Query();

/* ======       separate Criteria   ======
        query.addCriteria(Criteria.where("email").exists(true));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
 */
        /*
        ========== call Criteria's combine =========

         */
        Criteria criteria = new Criteria();
        query.addCriteria(criteria.andOperator(
                Criteria.where("email").regex("^[A-Za-z0-9+_.-]+@(.+)$"),
                Criteria.where("sentimentAnalysis").is(true)
        ));

        return mongoTemplate.find(query, User.class);
    }
}
