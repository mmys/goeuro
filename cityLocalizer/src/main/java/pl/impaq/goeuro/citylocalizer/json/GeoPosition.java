package pl.impaq.goeuro.citylocalizer.json;

import java.math.BigDecimal;

import com.google.api.client.util.Key;

/**
 * @author mmys
 * 
 *         Geo position json object model.
 *
 */
public class GeoPosition {

   @Key
   private BigDecimal latitude;

   @Key
   private BigDecimal longitude;

   public BigDecimal getLatitude() {
      return latitude;
   }

   public BigDecimal getLongitude() {
      return longitude;
   }

   @Override
   public String toString() {
      return "GeoPosition [latitude=" + latitude + ", longitude=" + longitude + "]";
   }

}
