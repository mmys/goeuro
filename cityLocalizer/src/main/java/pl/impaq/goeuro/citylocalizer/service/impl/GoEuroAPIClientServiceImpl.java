package pl.impaq.goeuro.citylocalizer.service.impl;

import java.io.IOException;

import pl.impaq.goeuro.citylocalizer.exceptions.DataParseException;
import pl.impaq.goeuro.citylocalizer.exceptions.GoEuroAPIException;
import pl.impaq.goeuro.citylocalizer.json.Localization;
import pl.impaq.goeuro.citylocalizer.service.GoEuroAPIClientService;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;

/**
 * @author mmys
 *
 */
public class GoEuroAPIClientServiceImpl implements GoEuroAPIClientService {

   public Localization[] queryGoEuroAPIForCityLocalization(String cityName) throws GoEuroAPIException,
         DataParseException {

      HttpResponse response = sendHttpRequestToGoEuro(cityName);
      Localization[] cityLocalizations = parseGoEuroHttpResponse(response);
      printReceivedLocalizations(cityLocalizations);

      return cityLocalizations;
   }

   private HttpResponse sendHttpRequestToGoEuro(String cityName) throws GoEuroAPIException {
      HttpRequestFactory requestFactory = createHttpRequestFactory();
      GoEuroUrl url = GoEuroUrl.getCityLocalizations(cityName);

      try {
         HttpRequest request = requestFactory.buildGetRequest(url);
         HttpResponse response = request.execute();
         if (response != null && response.getStatusCode() == 200) {
            return response;
         } else {
            throw new GoEuroAPIException("Unexpected response code from GoEuro: " + response.getStatusCode());
         }

      } catch (HttpResponseException e) {
         System.err.println(e.getStatusMessage());
         throw new GoEuroAPIException("Faild to send request to GoEuro: " + e.getMessage());
      } catch (IOException e) {
         System.err.println(e.getMessage());
         throw new GoEuroAPIException("Faild to send request to GoEuro: " + e.getMessage());
      }
   }

   private Localization[] parseGoEuroHttpResponse(HttpResponse response) throws DataParseException {

      try {
         return response.parseAs(Localization[].class);
      } catch (Exception e) {
         System.err.println(e.getMessage());
         throw new DataParseException("Faild to parse response from GoEuro: " + e.getMessage());
      }
   }

   private HttpRequestFactory createHttpRequestFactory() {
      HttpTransport httpTransport = new NetHttpTransport();
      final JsonFactory jsonFactory = new JacksonFactory();

      return httpTransport.createRequestFactory(new HttpRequestInitializer() {
         public void initialize(HttpRequest request) {
            request.setParser(new JsonObjectParser(jsonFactory));
         }
      });
   }

   private void printReceivedLocalizations(Localization[] cityLocalizations) {

      if (cityLocalizations != null && cityLocalizations.length > 0) {
         System.out.println("\n ---------- Localizations from GoEuro ---------- \n");
         for (Localization loc : cityLocalizations) {
            System.out.println(loc.toString() + "\n");
         }
         System.out.println(" ------------------------------------------------ ");
      } else {
         System.out.println("Localizations not found - empty response from GoEuro.");
      }
   }

   private static class GoEuroUrl extends GenericUrl {
      static final String GO_EURO_URL_PREFIX = "http://api.goeuro.com/api/v2/position/suggest/en/";

      public GoEuroUrl(String encodedUrl) {
         super(encodedUrl);
      }

      public static GoEuroUrl getCityLocalizations(String cityName) {
         return new GoEuroUrl(GO_EURO_URL_PREFIX + cityName);
      }
   }

}
