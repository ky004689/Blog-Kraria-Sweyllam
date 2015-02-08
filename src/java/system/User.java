/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system;

import java.awt.Image;

/**
 *
 * @author Yo
 */
public class User {
    
    UserStatus status;
    Role role;
    
    String firstname, lastname, about, username, password, last_connected;
    Image photo;
    
    public User(String firstname, String lastname, String about, String username, String password, String last_connected, Role role, UserStatus status, Image photo){
        
        this.firstname = firstname;
        this.lastname = lastname;
        this.about = about;
        this.username = username;
        this.password = password;
        this.last_connected = last_connected;
        this.role = role;
        this.status = status;
    }
}
