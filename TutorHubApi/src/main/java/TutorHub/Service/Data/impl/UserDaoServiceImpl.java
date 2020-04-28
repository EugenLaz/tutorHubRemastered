package TutorHub.Service.Data.impl;


import TutorHub.Service.Data.Repository.UserRepository;
import TutorHub.Service.Data.UserDaoService;
import TutorHub.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDaoServiceImpl implements UserDaoService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getByUserName(String username) {
        Optional<User> user = userRepository.findById(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return user.get();
    }

    @Override
    public List<User> getAll() {
        List<User> user = userRepository.findAll();
        return user;
    }

    @Override
    public boolean saveUser(User user) {
        System.out.println(user.toString());
        userRepository.save(user);
        return true;
    }


    @Override
    public boolean deleteUser(String username) {
        try {
            userRepository.deleteById(username);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
        return true;
    }

    @Override
    public List<User> getAllTutors() {
        return userRepository.getAllTutors();
    }

//    returns -1  if user with the given username exists in table
//    returns 0 if user with the given email exists in table
    @Override
    public Integer checkIfUnique(User user) {
        List<Integer> resultSet;
        int result;
        resultSet = userRepository.checkIfExists(user.getUsername(), user.getEmail());
        if (resultSet.isEmpty()) result = 1;
        else result=resultSet.get(0);
        return result;
    }


}
