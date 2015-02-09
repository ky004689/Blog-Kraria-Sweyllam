/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miage.ntdp.bog.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lotus
 */
@Entity
@Table(name = "ARTICLES")
@XmlRootElement

public class Articles implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
  
    private Integer articleId;
    private String title;
    @Size(min = 1, max = 1000)
  
    private String content;
  
    @Temporal(TemporalType.DATE)
    private Date postedOn;

    public Articles() {
    }

    public Articles(Integer articleId) {
        this.articleId = articleId;
    }

    public Articles(String title, String content) {
        this.title = title;
        this.content = content;
        this.postedOn = postedOn;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPostedOn() {
        return postedOn;
    }

    public void setPostedOn(Date postedOn) {
        this.postedOn = postedOn;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (articleId != null ? articleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Articles)) {
            return false;
        }
        Articles other = (Articles) object;
        if ((this.articleId == null && other.articleId != null) || (this.articleId != null && !this.articleId.equals(other.articleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Article[ id=" + articleId + ", title=" + title + ", content=" + content + ", time=" + postedOn + "]";
    }
    
}
