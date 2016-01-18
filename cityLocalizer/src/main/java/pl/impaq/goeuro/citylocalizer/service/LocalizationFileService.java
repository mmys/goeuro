package pl.impaq.goeuro.citylocalizer.service;

import pl.impaq.goeuro.citylocalizer.exceptions.CSVFileException;
import pl.impaq.goeuro.citylocalizer.json.Localization;

/**
 * Interface for localization file generation service
 * 
 * @author mmys
 *
 */
public interface LocalizationFileService {	
	
	/**
	 * @param localizationData - city localization data received from GoEuro site
	 * @param fileLocation - file directory
	 * @throws CSVFileException - csv file creation exception
	 */
	void convertLocalizationDataToCSVFile(Localization[] cityLocalizations, String fileLocation) throws CSVFileException ;

}
