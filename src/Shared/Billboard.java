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
	int ID;

	String topText;
	String bottomText;
	Color textColor;
	Color backgroundColor;
	Image image;

	String author; // TODO: A proper way of identifying people
}
