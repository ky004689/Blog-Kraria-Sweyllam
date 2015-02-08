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
    
    String title, keywords, published_on, content, position_name;
    Long position_latitude, position_longitude;
    Image photo;
    
}
