package com.example.collectionsApp.service;

import com.example.collectionsApp.models.Role;
import com.example.collectionsApp.models.User;
import com.example.collectionsApp.repositories.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger log = Logger.getLogger(UserService.class);


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public boolean add(User user) {

        User userFromDb = userRepository.findByUsername(user.getUsername());
        try {
            if (userFromDb != null) {
                log.debug("add user. (already exists " + user.getUsername() + ")");
                return false;
            }
            if (user.getUsername().equals("admin"))
                user.setRoles(Collections.singleton(Role.ADMIN));
            else
                user.setRoles(Collections.singleton(Role.USER));

            user.setDataRegistration(new Date());
            user.setActivationCode(UUID.randomUUID().toString());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setUrl("https://st.depositphotos.com/1779253/5140/v/600/depositphotos_51402215-stock-illustration-male-avatar-profile-picture-use.jpg");
            userRepository.save(user);
            log.info("add user. (name: " + user.getUsername() + ")");
            return true;
        }
        catch (Exception e) {
            log.error("add user. (name: " + user.getUsername() + ")");
            log.error(e.getMessage());
            return false;
        }

    }

    public boolean changeUser(User user) {
        User userDB = userRepository.findByUsername(user.getUsername());
        try {
            if (userDB != null){
                userRepository.save(user);
                log.info("change user. (name: " + user.getUsername() + ")");
                return true;
            }
            else {
                log.debug("change user. (does not exist " + user.getUsername() + ")");
                return false;
            }
        }
        catch (Exception e) {
            log.error("change user. (name: " + user.getUsername() + ")");
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean deleteById(long id) {
        User userFromDb = findById(id);
        try {
            if (userFromDb != null) {
                userRepository.deleteById(id);
                log.info("delete user. (name: " + userFromDb.getUsername() + ")");
                return true;
            }
            else {
                log.debug("delete user. (does not exist " + userFromDb.getUsername() + ")");
                return false;
            }
        }
        catch (Exception e){
            log.error("delete user. (name: " + userFromDb.getUsername() + ")");
            log.error(e.getMessage());
            return false;
        }

    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    public User findByName(String name) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(name));
        return userOptional.orElse(null);
    }

    public boolean isAuthentication() {
        return getAuthenticationID() != 0;
    }

    public boolean isAdmin() {
        if (isAuthentication()) {
            return Role.ADMIN.toString().equals(getAuthenticationUser().getUserRole());
        }
        return false;
    }

    public long getAuthenticationID() {
        User user = getAuthenticationUser();
        if (user != null)
            return user.getId();
        else
            return 0;
    }

    public String getAuthenticationName() {
        User user = getAuthenticationUser();
        if (user != null)
            return user.getUsername();
        else
            return "";
    }

    public User getAuthenticationUser() {
        User user = null;
        try {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
        return user;
    }

    public int sizeAllUsers() {
        return userRepository.findAll().size();
    }

    public void setRoleUser(List<User> users) {
        for (User user : users) {
            user.changeRole(Role.USER);
            changeUser(user);
        }
    }

    public void setRoleAdmin(List<User> users) {
        for (User user : users) {
            user.changeRole(Role.ADMIN);
            changeUser(user);
        }
    }


}