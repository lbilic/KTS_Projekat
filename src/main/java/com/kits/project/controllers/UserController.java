package com.kits.project.controllers;

import com.kits.project.DTOs.LoginDTO;
import com.kits.project.DTOs.TokenDTO;
import com.kits.project.DTOs.UserDTO;
import com.kits.project.exception.BadRequestException;
import com.kits.project.exception.ForbiddenException;
import com.kits.project.model.AccountAuthority;
import com.kits.project.model.User;
import com.kits.project.security.JWTUtils;
import com.kits.project.services.interfaces.AccountAuthorityServiceInterface;
import com.kits.project.services.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@CrossOrigin(value = "http://localhost:4200")
@RequestMapping("api")
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserServiceInterface userServiceInterface;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountAuthorityServiceInterface accountAuthorityServiceInterface;

    @RequestMapping(
            value = "/login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity login(@Valid @RequestBody LoginDTO loginDTO, BindingResult errors) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(), loginDTO.getPassword());
            authenticationManager.authenticate(token);
            User account = this.userServiceInterface.findByUsername(loginDTO.getUsername());
            if(!account.isConfirmed())
                throw new ForbiddenException("Account not confirmed!");

            UserDetails details = userDetailsService.loadUserByUsername(loginDTO.getUsername());

            Long id = account.getId();
            TokenDTO userToken = new TokenDTO(jwtUtils.generateToken(details, id, account.getAccountAuthorities()));
            return new ResponseEntity<>(userToken, HttpStatus.OK);
        } catch(ForbiddenException ex) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(
            value = "/check_username",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity checkUsername(
            @RequestParam("username") String username) {

        if (username == null || username.equals(""))
            throw new BadRequestException("Username can't be empty!");

        return new ResponseEntity(this.userServiceInterface.isUsernameTaken(username), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/users/get_all",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ArrayList<User>> getAllUsers() {

        ArrayList<User> allUsers = userServiceInterface.getAllUsers();

        return new ResponseEntity(allUsers, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/users/remove_user",
            method = RequestMethod.POST,
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity removeUser(@RequestBody String username) {
        if (username == null || username.equals(""))
            throw new BadRequestException("Username can't be empty!");

        User account = this.userServiceInterface.findByUsername(username);
        account.setDeleted(true);
        userServiceInterface.save(account);

        return new ResponseEntity(HttpStatus.OK);
    }
}