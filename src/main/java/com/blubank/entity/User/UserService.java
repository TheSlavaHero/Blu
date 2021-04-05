package com.blubank.entity.User;

import com.blubank.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void deleteUsers(List<Long> ids) {
        ids.forEach(id -> {
            Optional<User> user = userRepository.findById(id);
            user.ifPresent(u -> {
                if ( ! AppConfig.ADMIN.equals(u.getEmail())) {
                    userRepository.deleteById(u.getId());
                }
            });
        });
    }

    @Transactional
    public boolean addUser(String name, String surname,
                           String passHash, UserRole role,
                           String email, String phone,
                           String age) {
        if (userRepository.existsByEmail(email))
            return false;

        User user = new User(name, surname, passHash, role, email, phone, age);
        userRepository.save(user);
//        send(email, user.getAuthKey());

        return true;
    }

    @Transactional
    public boolean checkUser(String email) {//returns true if exists
        if (userRepository.existsByEmail(email)) {
            return true;
        } else {
            return false;
        }
    }


    @Transactional
    public void updateUser(String email, String phone, String age) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            return;

        user.setEmail(email);
        user.setPhone(phone);
        user.setAge(age);

        userRepository.save(user);
    }

    @Transactional
    public void updateUser(String email, String authKey) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            return;
        user.setAuthKey(authKey);

        userRepository.save(user);
    }
}
