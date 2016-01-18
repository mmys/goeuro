package pl.impaq.goeuro.citylocalizer.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import pl.impaq.goeuro.citylocalizer.exceptions.CSVFileException;
import pl.impaq.goeuro.citylocalizer.json.Localization;
import pl.impaq.goeuro.citylocalizer.service.LocalizationFileService;

/**
 * @author mmys
 *
 */
public class LocalizationFileServiceImpl implements LocalizationFileService {

   static final String DELIMITER = ",";
   static final String TEXT_QUALIFIER = "\"";
   static final String ENCODING = "UTF-8";
   static final String EMPTY = "";
   static final String FILE_PREFIX = "loclizations_of_";

   private String cityName;

   public LocalizationFileServiceImpl(String cityName) {
      super();
      this.cityName = cityName;
   }

   public void convertLocalizationDataToCSVFile(Localization[] cityLocalizations, String fileLocation)
         throws CSVFileException {

      File file = new File(new File(fileLocation), FILE_PREFIX + cityName + "_" + System.currentTimeMillis() + ".csv");

      FileOutputStream fos = null;
      FileChannel channel = null;
      try {
         fos = new FileOutputStream(file);
         channel = fos.getChannel();

         generateFileHeader(channel);
         generateFileContent(channel, cityLocalizations);
      } catch (Exception e) {
         System.err.println(e.getMessage());
         throw new CSVFileException("Faild to generate csv file: " + e.getMessage());
      } finally {
         try {
            if (fos != null) {
               fos.flush();
               fos.close();
            }
            if (channel != null) {
               channel.close();
            }
         } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new CSVFileException("Faild to generate csv file: " + e.getMessage());
         }
      }
   }

   protected void generateFileContent(FileChannel channel, Localization[] localizations) throws IOException,
         UnsupportedEncodingException {
      if (localizations != null) {
         StringBuilder sb = new StringBuilder();
         for (Localization localization : localizations) {
            sb.append(v(localization.get_id())).append(DELIMITER);
            sb.append(s(localization.getName())).append(DELIMITER);
            sb.append(s(localization.getType())).append(DELIMITER);
            sb.append(v(localization.getGeo_position().getLatitude())).append(DELIMITER);
            sb.append(v(localization.getGeo_position().getLongitude())).append("\n");
            channel.write(ByteBuffer.wrap(sb.toString().getBytes(ENCODING)));
            sb.setLength(0);
         }
      }
   }

   protected void generateFileHeader(FileChannel channel) throws UnsupportedEncodingException, IOException {
      StringBuilder sb = new StringBuilder();
      sb.append("_id").append(DELIMITER).append("name").append(DELIMITER).append("type").append(DELIMITER)
            .append("latitude").append(DELIMITER).append("longitude").append("\n");
      channel.write(ByteBuffer.wrap(sb.toString().getBytes(ENCODING)));
   }

   protected Object v(Object o) {
      if (o == null) {
         return EMPTY;
      } else {
         return o;
      }
   }

   protected String s(String s) {
      String value = (String) v(s);
      if (value != null && !value.isEmpty()) {
         return TEXT_QUALIFIER + value.replaceAll("\n", " ").replaceAll("\r", " ") + TEXT_QUALIFIER;
      } else {
         return value;
      }
   }
}
