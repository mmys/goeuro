package pl.impaq.goeuro.citylocalizer.exceptions;

/**
 * Exception thrown when csv file cannot be generated
 * 
 * @author mmys
 * 
 */
public class CSVFileException extends CityLocalizatorException {
	
	private static final long serialVersionUID = 7242192528561347585L;

	public CSVFileException(String message) {
		super(message);		
	}
}
