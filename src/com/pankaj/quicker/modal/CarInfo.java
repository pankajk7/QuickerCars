package com.pankaj.quicker.modal;

import java.io.Serializable;

public class CarInfo implements Serializable{
	private static final long serialVersionUID = 3999516139540543473L;
	String name;
	String image;
	String price;
	String brand;
	String type;
	String rating;
	String color;
	String engine_cc;
	String mileage;
	String abs_exist;
	String description;
	String link;
	City[] cities;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getEngine_cc() {
		return engine_cc;
	}
	public void setEngine_cc(String engine_cc) {
		this.engine_cc = engine_cc;
	}
	public String getMileage() {
		return mileage;
	}
	public void setMileage(String mileage) {
		this.mileage = mileage;
	}
	public String getAbs_exist() {
		return abs_exist;
	}
	public void setAbs_exist(String abs_exist) {
		this.abs_exist = abs_exist;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public City[] getCities() {
		return cities;
	}
	public void setCities(City[] cities) {
		this.cities = cities;
	}
}
