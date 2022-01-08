package com.rony.oracleemployee.services;

import com.rony.oracleemployee.dtos.request.UserInfoDto;
import com.rony.oracleemployee.dtos.request.UserLoginDto;
import com.rony.oracleemployee.exception.ResourceNotFoundException;
import com.rony.oracleemployee.model.User;
import com.rony.oracleemployee.repository.CountryRepository;
import com.rony.oracleemployee.repository.RoleRepository;
import com.rony.oracleemployee.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CountryService countryService;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void addUser(UserInfoDto userInfoDto){
        var user = new User();
        BeanUtils.copyProperties(userInfoDto,user);
        user.setCountry(countryRepository.getCountryByCountryCode (userInfoDto.getCountryCode ()));
        String dateOfBirth = userInfoDto.getDateOfBirth() == null ? "2017-11-15" : userInfoDto.getDateOfBirth();
        user.setDateOfBirth(LocalDate.parse(dateOfBirth));
        // Bcrypt password
        user.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));
        if(userInfoDto.getRoles() == null ){
            roleRepository.findByName ("USER").ifPresent (role -> {
                user.setRoles (List.of (role.getName ()));
                log.info("adding default role : {}, tp user : {}", role.getName(), user.getUsername());
            });
        }

        tokenService.generateToken (user);
        userRepository.save(user);
    }

//    public void addRoleToUser(String username, String roleName) {
//
//        var optionalUser = userRepository.findByUsername(username);
//        var optionalRole = roleRepository.findByName(roleName);
//        log.info("optionalUser  : {}  optionalRole : {}", optionalUser.get().getUsername(), optionalRole.get().getName());
//        if(optionalUser.isPresent() && optionalRole.isPresent()){
//            log.info("Adding role : {} to user : {}", roleName, username);
//            optionalUser.get().getRoles().add(optionalRole.get());
//        }else{
//            log.error("Cannot add role : {} to user : {}", roleName, username);
//        }
//    }

    public UserInfoDto loginUser(UserLoginDto userLoginDto){
      var userModel = getByUserName(userLoginDto.getUsername());
        if (userModel != null) {
            if (passwordEncoder.matches(userLoginDto.getPassword(), userModel.getPassword())) {
                var userInfoDto = new UserInfoDto();
                BeanUtils.copyProperties(userModel, userInfoDto);

                userInfoDto.setCountryCode (userModel.getCountry ().getCountryCode ());
                userInfoDto.setPassword ("******");
                log.info("logged in user : {}", userModel.getUsername());
                return userInfoDto;
            } else {
                log.error("password mismatched for user : {}",userLoginDto.getUsername());
                throw new BadCredentialsException("Password mismatched");
            }
        } else {
            log.error("invalid username : {}",userLoginDto.getUsername());
            throw new BadCredentialsException("Invalid username");
        }
    }

    public User getByUserName(String username){
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User userModel = optionalUser.get();
            return userModel;
        }else{
            log.error("user not found with this username : {}",username);
            throw  new ResourceNotFoundException("user not found with this username : "+username);

        }
    }


    public User getById(long id) throws ResourceNotFoundException{
        var user =  this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
        log.info("getting user : {}, of id : {}", user.getUsername(), id);
        return user;
    }

    public List<User> getAllUsers(){
        log.info("getting all users ");
        return userRepository.findAll();
    }

    public void deleteUserById(long id) throws ResourceNotFoundException{
        var user =  this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
        log.info("deleting user : {}, of id {}", user.getUsername(), id);
        userRepository.deleteById(id);
    }

    public void updateUser(User user, long id) throws ResourceNotFoundException{
        var prevUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
        log.info("updating user : {}, of id : {}", user.getUsername(), id);
        userRepository.save(user);
    }

}
