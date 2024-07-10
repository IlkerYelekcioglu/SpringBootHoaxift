package com.hoaxify.ws.user;

import com.hoaxify.ws.configuration.CurrentUser;
import com.hoaxify.ws.email.EmailService;
import com.hoaxify.ws.user.dto.UserUpdate;
import com.hoaxify.ws.user.exception.ActivationNotificationException;
import com.hoaxify.ws.user.exception.NotFoundException;
import com.hoaxify.ws.user.exception.NotUniqueEmailException;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmailService emailService;

    @Transactional(rollbackOn = {MailException.class})
    public void save(User user){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActivationToken(UUID.randomUUID().toString());
            userRepository.saveAndFlush(user);
            emailService.sendActivationEmail(user.getEmail(), user.getActivationToken());
        }catch (DataIntegrityViolationException exception){
            throw new NotUniqueEmailException();
        }catch (MailException exception){
            throw new ActivationNotificationException();
        }

    }


  public void activateUser(String token) {
        User inDB = userRepository.findByActivationToken(token);
        if(inDB == null){
            throw new ActivationNotificationException();
        }else {
            inDB.setActive(true);
            inDB.setActivationToken(null);
        }
  }

  public Page<User> getUsers(Pageable page, CurrentUser currentUser) {
      if(currentUser == null) {
        return userRepository.findAll(page);
      }
      return userRepository.findByIdNot(currentUser.getId(),page);
      }

  public User getUser(long id) {
      return userRepository.findById(id).orElseThrow(()-> new NotFoundException(id));
  }

  public User findByEmail(String email) {
     return userRepository.findByEmail(email);
    }

  public User updateUser(long id, UserUpdate userUpdate) {
      User inDb = getUser(id);
      inDb.setPassword(userUpdate.username());
      return  userRepository.save(inDb);
  }
}
