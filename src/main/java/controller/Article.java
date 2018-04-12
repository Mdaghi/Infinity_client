/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.guigarage.controls.Media;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

/**
 *
 * @author dainer
 */
class Article implements Media {

    private String content;

    private String title;

    private String coverUrl;

    private String author;

    private String url;

    private String publishedAt;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    @Override
    public String toString() {
        return "Article{" + "content=" + content + ", coverUrl=" + coverUrl + ", author=" + author + ", url=" + url + '}';
    }

    @Override
    public StringProperty titleProperty() {
        return new SimpleStringProperty(getAuthor());
    }

    @Override
    public StringProperty descriptionProperty() {
        return new SimpleStringProperty(getContent());
    }

    @Override
    public ObjectProperty<Image> imageProperty() {
        return new SimpleObjectProperty<>(new Image(getCoverUrl(), true));
    }
}
