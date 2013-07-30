package minesweeper.views.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

/**
 *  Views Helper Methods
 *  @author     Lidan Hifi
 *  @version    1.0
 */
public class ViewUtilities {
	
	/**
	 * get scale factor for scaling images
	 * @param sourceSize
	 * @param targetSize
	 * @return image scale factor
	 */
	private static double getScaleFactor(int sourceSize, int targetSize) {
	    double dScale = 1;
	    if (sourceSize > targetSize) {
	        dScale = (double) targetSize / (double) sourceSize;
	    } else {
	        dScale = (double) targetSize / (double) sourceSize;
	    }

	    return dScale;
	}
	
	/**
	 * get scale factor to fill the contains panel
	 * @param sourceSize
	 * @param targetSize
	 * @return image scale factor
	 */
	private static double getScaleFactorToFill(Dimension sourceSize, Dimension targetSize) {
	    double dScaleWidth = getScaleFactor(sourceSize.width, targetSize.width);
	    double dScaleHeight = getScaleFactor(sourceSize.height, targetSize.height);

	    // Max - scale by panel height
	    // Min - scale by panel width
	    double dScale = Math.max(dScaleHeight, dScaleWidth);
	    return dScale;
	}
	
	/**
	 * Resizes an image using a Graphics2D object backed by a BufferedImage.
	 * @param image - source image to scale
	 * @param size	- target size
	 * @return - the new resized image
	 */
	public static Image getScaleImage(Image image, Dimension size) {
		double scaleFactor = Math.min(1d, getScaleFactorToFill(new Dimension(image.getWidth(null), image.getHeight(null)), size));

		// calculate the image width & height by scale factor
	    int scaleWidth = (int) Math.round(image.getWidth(null) * scaleFactor);
	    int scaleHeight = (int) Math.round(image.getHeight(null) * scaleFactor);
	    
	    // resize image by scale factor
	    BufferedImage resizedImg = new BufferedImage(scaleWidth, scaleHeight, Transparency.TRANSLUCENT);
	    Graphics2D g2 = resizedImg.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(image, 0, 0, scaleWidth, scaleHeight, null);
	    g2.dispose();
	    return resizedImg;
	    
	}
	
	/**
	 * convert color object to hexadecimal string
	 * @param color
	 * @return hexadecimal string for HTML Colors
	 */
	public static String convertToHex(Color color) {
		String hex = Integer.toHexString(color.getRGB() & 0xffffff);
		if (hex.length() < 6) {
		    hex = "0" + hex;
		}
		return "#" + hex;
	}
}
