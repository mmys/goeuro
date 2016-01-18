package pl.impaq.goeuro.citylocalizer.exceptions;

/**
 * Exception thrown when GoEuro API fails
 * 
 * @author mmys
 * 
 */
public class GoEuroAPIException extends Exception {

	private static final long serialVersionUID = 7242762528561347585L;

	public GoEuroAPIException(String message) {
		super(message);
	}
}
