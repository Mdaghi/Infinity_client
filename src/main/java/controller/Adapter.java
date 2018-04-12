/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import tn.esprit.infinity_server.persistence.*;


/**
 *
 * @author dainer
 */
public class Adapter {

    static Newsource newsSourceAdaptation(NewsSource newssourceToAdapt) {
        Newsource newsource = new Newsource();
        newsource.setId(newssourceToAdapt.getId());
        newsource.setName(newssourceToAdapt.getTitle());
        newsource.setDescription(newssourceToAdapt.getDescription());
        newsource.setCoverUrl(newssourceToAdapt.getImage());
        newsource.setUrl(newssourceToAdapt.getUrl());
        return newsource;
    }
    
    static NewsSource newsSourceAdapationReverse(Newsource newssourceToAdapt)
    {
    	NewsSource newssource = new NewsSource();
    	newssource.setId(newssourceToAdapt.getId());
    	newssource.setDescription(newssourceToAdapt.getDescription());
    	newssource.setImage(newssourceToAdapt.getCoverUrl());
    	newssource.setUrl(newssourceToAdapt.getUrl());
    	return newssource;
    }

    static Article articleAdaptation(NewsArticle articleToAdapt) {
        Article article = new Article();
        article.setAuthor(articleToAdapt.getAuthor());
        article.setContent(articleToAdapt.getDescription());
        article.setCoverUrl(articleToAdapt.getUrlToImage());
        article.setPublishedAt(articleToAdapt.getPublishedAt());
        article.setTitle(articleToAdapt.getTitle());
        article.setUrl(articleToAdapt.getUrl());
        return article;
    }
    
    static NewsArticle articleAdptationReverse(Article articleToAdapt)
    {
    	NewsArticle article = new NewsArticle();
    	article.setAuthor(articleToAdapt.getAuthor());
    	article.setDescription(articleToAdapt.getContent());
    	article.setPublishedAt(articleToAdapt.getPublishedAt());
    	article.setTitle(articleToAdapt.getTitle());
    	article.setUrl(articleToAdapt.getUrl());
    	article.setUrlToImage(articleToAdapt.getCoverUrl());
    	return article;
    }

}
