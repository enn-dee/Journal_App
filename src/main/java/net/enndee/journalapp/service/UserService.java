package net.enndee.journalapp.service;

import net.enndee.journalapp.entity.User;
import net.enndee.journalapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user){
        userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

//    public Optional<User> findUserById(ObjectId id){
//        return userRepository.findById(id);
//    }
//    public void deleteUserById(ObjectId id){
//        userRepository.deleteById(id);
//    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
