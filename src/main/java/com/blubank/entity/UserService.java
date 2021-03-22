package com.blubank.entity;

import com.blubank.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.blubank.mail.SendAuthCodeViaMail.send;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Transactional
    public void deleteUsers(List<Long> ids) {
        ids.forEach(id -> {
            Optional<User> user = userRepository.findById(id);
            user.ifPresent(u -> {
                if ( ! AppConfig.ADMIN.equals(u.getLogin())) {
                    userRepository.deleteById(u.getId());
                }
            });
        });
    }

    @Transactional
    public boolean addUser(String login, String passHash,
                           UserRole role, String email,
                           String phone, String age) {
        if (userRepository.existsByLogin(login))
            return false;

        User user = new User(login, passHash, role, email, phone, age);
        userRepository.save(user);
//        send(email, user.getAuthKey());

        return true;
    }

    @Transactional
    public void updateUser(String login, String email, String phone, String age) {
        User user = userRepository.findByLogin(login);
        if (user == null)
            return;

        user.setEmail(email);
        user.setPhone(phone);
        user.setAge(age);

        userRepository.save(user);
    }

    @Transactional
    public void updateUser(String login, Boolean authentication, String authKey) {
        User user = userRepository.findByLogin(login);
        if (user == null)
            return;

        user.setAuthentication(authentication);
        user.setAuthKey(authKey);

        userRepository.save(user);
    }

    public void addUser(String admin, String password, UserRole admin1, String s, String s1) {
    }
}
