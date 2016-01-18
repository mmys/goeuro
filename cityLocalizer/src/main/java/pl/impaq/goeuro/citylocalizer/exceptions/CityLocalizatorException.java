package pl.impaq.goeuro.citylocalizer.exceptions;

/**
 * Main CityLocalizator exception
 * 
 * @author mmys
 * 
 */
public class CityLocalizatorException extends Exception {

   private static final long serialVersionUID = -7119806583016673997L;

   public CityLocalizatorException(Throwable e) {
      super(e);
   }

   public CityLocalizatorException(String message) {
      super(message);
   }

}
