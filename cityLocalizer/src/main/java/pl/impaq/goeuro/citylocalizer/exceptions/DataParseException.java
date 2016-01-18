package pl.impaq.goeuro.citylocalizer.exceptions;

/**
 * Exception thrown when json data are invalid
 * 
 * @author mmys
 * 
 */
public class DataParseException extends Exception {

   private static final long serialVersionUID = 7242762528561347585L;

   public DataParseException(String message) {
      super(message);
   }
}
