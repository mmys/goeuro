package pl.impaq.goeuro.citylocalizer;

import java.io.File;

import pl.impaq.goeuro.citylocalizer.exceptions.CSVFileException;
import pl.impaq.goeuro.citylocalizer.exceptions.DataParseException;
import pl.impaq.goeuro.citylocalizer.exceptions.GoEuroAPIException;
import pl.impaq.goeuro.citylocalizer.json.Localization;
import pl.impaq.goeuro.citylocalizer.service.GoEuroAPIClientService;
import pl.impaq.goeuro.citylocalizer.service.LocalizationFileService;
import pl.impaq.goeuro.citylocalizer.service.impl.GoEuroAPIClientServiceImpl;
import pl.impaq.goeuro.citylocalizer.service.impl.LocalizationFileServiceImpl;

/**
 * 
 * Main class for CityLocalizer application. It queries for locations of given
 * city at GoEuro site. Collected data are stored in csv file.
 * 
 * @author mmys
 *
 */
public class CityLocalizer {

   /**
    * @param args
    *           - city name and path to directory where the csv file should be
    *           stored
    */
   public static void main(String[] args) {

      if (argumentsAreValid(args)) {
         try {
            Localization[] cityLocalizations = queryGoEuroSite(args);
            if (cityLocalizations != null && cityLocalizations.length > 0) {
               storeLocalizationDataToFile(args, cityLocalizations);
            }

         } catch (GoEuroAPIException e) {
            System.err.println("Request to GoEuro has failed: " + e.getMessage());
         } catch (DataParseException e) {
            System.err.println("Parsing GoEuro response has failed: " + e.getMessage());
         } catch (CSVFileException e) {
            System.err.println("Generating csv file has failed: " + e.getMessage());
         }
      } else {
         printInstructionsForUser();
      }
   }

   private static void storeLocalizationDataToFile(String[] args, Localization[] cityLocalizations)
         throws CSVFileException {
      System.out.println("\n Storing localizations in csv file in: " + args[1]);
      LocalizationFileService localizationFileService = new LocalizationFileServiceImpl(args[0]);
      localizationFileService.convertLocalizationDataToCSVFile(cityLocalizations, args[1]);
   }

   private static Localization[] queryGoEuroSite(String[] args) throws GoEuroAPIException, DataParseException {
      System.out.println("Searching localizations for city: " + args[0]);
      GoEuroAPIClientService goEuroClient = new GoEuroAPIClientServiceImpl();
      Localization[] cityLocalizations = goEuroClient.queryGoEuroAPIForCityLocalization(args[0]);
      return cityLocalizations;
   }

   private static boolean argumentsAreValid(String[] args) {

      final String CITY_NAME_REGEX = "[a-zA-Z]+";

      if (args == null || args.length != 2) {
         System.out.println("Arguments not provided.");
         return false;
      } else if (args[0] == null || args[0].isEmpty() || !args[0].matches(CITY_NAME_REGEX)) {
         System.out.println("Provided city name is invalid " + args[0]);
         return false;
      } else if (args[1] == null || args[1].isEmpty() || !new File(args[1]).exists()
            || !new File(args[1]).isDirectory()) {
         System.out.println("Provided directory path is invalid " + args[1]);
         return false;
      }
      return true;
   }

   private static void printInstructionsForUser() {
      System.out.println("\n You must provide two arguments to this application:");
      System.out.println("   1. City name containing only letters e.g. Warsaw");
      System.out.println("   2. Existing directory path to save file e.g. C:\\abc\\xvc");

   }
}
