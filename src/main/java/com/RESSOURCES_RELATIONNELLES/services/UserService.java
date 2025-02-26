package com.RESSOURCES_RELATIONNELLES.services;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final HttpSession session;

    public UserService(HttpSession session) {
        this.session = session;
    }

    public boolean isLogged() {
        return session.getAttribute("IsUserConnectedToken") != null;
    }

    public void setAuthToken(){
        if (!isLogged()){
            session.setAttribute("IsUserConnectedToken", true);
        }
    }

    public void removeAuthToken(){
        if (isLogged()){
            session.removeAttribute("IsUserConnectedToken");
        }
    }

//    public boolean createUserInDatabase(User user){
//        try {
//            this.userRepository.findBy(
//        }
//        this.userRepository.save(user);
//        return true;
//    }

}
