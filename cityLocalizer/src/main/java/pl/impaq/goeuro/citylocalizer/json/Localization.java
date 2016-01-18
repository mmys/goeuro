package pl.impaq.goeuro.citylocalizer.json;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

/**
 * @author mmys
 * 
 *         Localization json object model.
 *
 */
public class Localization extends GenericJson {

   @Key
   private Long _id;

   @Key
   private String name;

   @Key
   private String fullName;

   @Key
   private String iata_airport_code;

   @Key
   private String type;

   @Key
   private String country;

   @Key
   private GeoPosition geo_position;

   @Key
   private String location_id;

   @Key
   private boolean inEurope;

   @Key
   private String countryCode;

   @Key
   private boolean coreCountry;

   @Key
   private String distance;

   public Long get_id() {
      return _id;
   }

   public String getName() {
      return name;
   }

   public String getFullName() {
      return fullName;
   }

   public String getIata_airport_code() {
      return iata_airport_code;
   }

   public String getType() {
      return type;
   }

   public String getCountry() {
      return country;
   }

   public GeoPosition getGeo_position() {
      return geo_position;
   }

   public String getLocation_id() {
      return location_id;
   }

   public boolean isInEurope() {
      return inEurope;
   }

   public String getCountryCode() {
      return countryCode;
   }

   public boolean isCoreCountry() {
      return coreCountry;
   }

   public String getDistance() {
      return distance;
   }

   @Override
   public String toString() {
      return "Localization [_id=" + _id + ", name=" + name + ", fullName=" + fullName + ", iata_airport_code="
            + iata_airport_code + ", type=" + type + ", country=" + country + ", geo_position=" + geo_position
            + ", location_id=" + location_id + ", inEurope=" + inEurope + ", countryCode=" + countryCode
            + ", coreCountry=" + coreCountry + ", distance=" + distance + "]";
   }

}
