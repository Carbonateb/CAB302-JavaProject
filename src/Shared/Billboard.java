package Shared;

import java.awt.*;

/**
 * The all mighty billboard class, the centrepiece of this assignment. This class represents the relevant info for a
 * billboard, including the information needed to display and identify them. Some additional metadata is also present,
 * for example, the author of the billboard (which is used in permissions)
 *
 * @author Lucas Maldonado
 */
public class Billboard {
	public int ID;

	public String topText;
	public String bottomText;
	public Color textColor;
	public Color backgroundColor;
	public Image image;

	public String author;


	/** Default constructor */
	public Billboard(int ID, String topText, String bottomText, Color textColor, Color backgroundColor, Image image, String author) {
		this.ID = ID;
		this.topText = topText;
		this.bottomText = bottomText;
		this.textColor = textColor;
		this.backgroundColor = backgroundColor;
		this.image = image;
		this.author = author;
	}
}
