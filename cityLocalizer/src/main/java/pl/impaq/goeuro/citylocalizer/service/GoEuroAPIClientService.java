package pl.impaq.goeuro.citylocalizer.service;

import pl.impaq.goeuro.citylocalizer.exceptions.GoEuroAPIException;
import pl.impaq.goeuro.citylocalizer.exceptions.DataParseException;
import pl.impaq.goeuro.citylocalizer.json.Localization;

/**
 * Interface for GoEuro API client service
 * 
 * @author mmys
 *
 */
public interface GoEuroAPIClientService {
	
	
	/**
	 * @param cityName - city name 
	 * @return city localization in json format
	 * @throws GoEuroAPIException - GoEuro API failure	
	 * @throws DataParseException - GoEuro response parse failure
	 */
	Localization[] queryGoEuroAPIForCityLocalization(String cityName) throws GoEuroAPIException, DataParseException;
}
