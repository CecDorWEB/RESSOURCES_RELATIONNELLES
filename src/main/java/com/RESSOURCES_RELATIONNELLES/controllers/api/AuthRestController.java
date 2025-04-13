package com.RESSOURCES_RELATIONNELLES.controllers.api;

import com.RESSOURCES_RELATIONNELLES.services.SecurityService;
import com.RESSOURCES_RELATIONNELLES.utils.apiModels.ApiAuthResponse;
import com.RESSOURCES_RELATIONNELLES.entities.User;
import com.RESSOURCES_RELATIONNELLES.services.UserService;
import com.RESSOURCES_RELATIONNELLES.utils.JwtUtils;
import com.RESSOURCES_RELATIONNELLES.utils.apiModels.LoginRequest;
import com.RESSOURCES_RELATIONNELLES.utils.apiModels.SignUpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    private final UserService _userService;
    private final PasswordEncoder _passwordEncoder;
    private final SecurityService _securityService;

    public AuthRestController(UserService userService, PasswordEncoder passwordEncoder, SecurityService securityService) {
        _userService = userService;
        _passwordEncoder = passwordEncoder;
        _securityService = securityService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        boolean isLogged = _securityService.login(loginRequest.email(), loginRequest.password());

        if (!isLogged) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou mot de passe incorrect");
        }

        User user = _userService.getUserByEmail(loginRequest.email());
        String token = JwtUtils.generateToken(user.getEmail());
        return ResponseEntity.ok(new ApiAuthResponse(token, user));
    }

    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest) {

        if(_securityService.userAlreadyExists(signUpRequest.email())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cet email est déjà utilisé");
        }

        User user = new User();
        user.setEmail(signUpRequest.email());
        user.setPassword(signUpRequest.password());
        user.setFirstName(signUpRequest.firstName());
        user.setLastName(signUpRequest.lastName());

        boolean signupOk = _securityService.signUpUser(user);

        if (!signupOk) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Données fournies incorrectes");
        }

        user = _userService.getUserByEmail(signUpRequest.email());
        String token = JwtUtils.generateToken(user.getEmail());
        return ResponseEntity.ok(new ApiAuthResponse(token, user));
    }
}

