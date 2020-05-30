package Shared;

import java.awt.*;
import java.io.Serializable;

/**
 * The all mighty billboard class, the centrepiece of this assignment. This class represents the relevant info for a
 * billboard, including the information needed to display and identify them. Some additional metadata is also present,
 * for example, the author of the billboard (which is used in permissions)
 *
 * @author Lucas Maldonado
 */
public class Billboard implements Serializable {
	public String name;

	public String titleText;
	public String infoText;
	public Color titleTextColor;
	public Color infoTextColor;
	public Color backgroundColor;
	public Image image;

	public String author;


	/** Default constructor */
	public Billboard(String name, String titleText, String infoText, Color titleTextColor, Color infoTextColor, Color backgroundColor, Image image, String author) {
		this.name = name;
		this.titleText = titleText;
		this.infoText = infoText;
		this.titleTextColor = titleTextColor;
		this.infoTextColor = infoTextColor;
		this.backgroundColor = backgroundColor;
		this.image = image;
		this.author = author;
	}
}
