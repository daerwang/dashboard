/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.model;

import org.apache.pdfbox.pdmodel.font.PDFont;

/**
 * The Class IrsFormCoordinate.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public class IrsFormCoordinate {
	
	/** The name. */
	private String name;
	
	/** The font. */
	private PDFont font;
	
	/** The font size. */
	private float fontSize;
	
	/** The x. */
	private float x;
	
	/** The y. */
	private float y;
	
	/** The text. */
	private String text = "";
	
	private Integer pageNumber;
	
	
	/**
	 * Instantiates a new irs form coordinate.
	 */
	public IrsFormCoordinate(){
		this.fontSize = 0;
	}
	
	/**
	 * Instantiates a new irs form coordinate.
	 *
	 * @param name the name
	 * @param x the x
	 * @param y the y
	 * @param fontSize the font size
	 * @param text the text
	 */
	public IrsFormCoordinate(String name, float x, float y, float fontSize, String text){
		this.x = x;
		this.y = y;
		this.text = text;
		this.fontSize = fontSize;
		this.name = name;
	}
	
	public IrsFormCoordinate(String name, float x, float y, float fontSize, String text, Integer pageNumber){
		this.x = x;
		this.y = y;
		this.text = text;
		this.fontSize = fontSize;
		this.name = name;
		this.pageNumber = pageNumber;
	}
	
	/**
	 * Instantiates a new irs form coordinate.
	 *
	 * @param x the x
	 * @param y the y
	 * @param text the text
	 */
	public IrsFormCoordinate(float x, float y, String text){
		this.x = x;
		this.y = y;
		this.text = text;
	}
	
	/**
	 * Gets the font.
	 *
	 * @return the font
	 */
	public PDFont getFont() {
		return font;
	}
	
	/**
	 * Gets the font size.
	 *
	 * @return the font size
	 */
	public float getFontSize() {
		return fontSize;
	}
	
	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public float getX() {
		return x;
	}
	
	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public float getY() {
		return y;
	}
	
	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Sets the font.
	 *
	 * @param font the new font
	 */
	public void setFont(PDFont font) {
		this.font = font;
	}
	
	/**
	 * Sets the font size.
	 *
	 * @param fontSize the new font size
	 */
	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}
	
	/**
	 * Sets the x.
	 *
	 * @param x the new x
	 */
	public void setX(float x) {
		this.x = x;
	}
	
	/**
	 * Sets the y.
	 *
	 * @param y the new y
	 */
	public void setY(float y) {
		this.y = y;
	}
	
	/**
	 * Sets the text.
	 *
	 * @param text the new text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	
}
