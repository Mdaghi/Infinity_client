/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.guigarage.controls.Media;
import java.awt.Color;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

/**
 *
 * @author dainer
 */
public class Newsource implements Media {

	private int id;
    private String description;
    private String coverUrl;
    private String url;
    private String name;

    public int getId()
    {
    	return id;
    }
    public void setId(int id)
    {
    	this.id=id;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    
    
    @Override
	public String toString() {
		return "Newsource [id=" + id + ", description=" + description + ", coverUrl=" + coverUrl + ", url=" + url
				+ ", name=" + name + "]";
	}
	@Override
    public StringProperty titleProperty() {
        return new SimpleStringProperty(getName());
    }

    @Override
    public StringProperty descriptionProperty() {
        return new SimpleStringProperty(getDescription());
    }

    @Override
    public ObjectProperty<Image> imageProperty() {
        return new SimpleObjectProperty<>(new Image(getCoverUrl(), true));
    }
}
