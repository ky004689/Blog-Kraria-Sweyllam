/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system;

import java.awt.Image;
import java.util.List;

/**
 *
 * @author Yo
 */
public class Article {
    
    List<Comment> comments;
    Status status;
    User author;
    
    String title, keywords, published_on, content, position_name;
    Long position_latitude, position_longitude;
    Image photo;
    
    public Article(String title, String keywords, String published_on, String content, String position_name, Long position_latitude, Long position_longitude, Image photo, Status status, User author){
        
        this.title = title;
        this.keywords = keywords;
        this.published_on = published_on;
        this.content = content;
        this.position_name = position_name;
        this.position_latitude = position_latitude;
        this.position_longitude = position_longitude;
        this.photo = photo;
        this.status = status;
        this.author = author;
        
    }
}
