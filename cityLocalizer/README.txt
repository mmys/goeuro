This is short instruction how to build and run
simple java application CityLocalizer:

1. Install java 1.7 and Maven 3
2. Open console and go to main project folder - cityLocalizer
2. To build execute following command:

	mvn clean install
	
3. Go to target folder to run application
4. Run application with follwoing command:

	java -jar citylocalizer-1.0.0.jar CITY_NAME FOLDER_PATH
	
	for example:
	
	java -jar citylocalizer-1.0.0.jar Warsow C:\abc\xyz
	
	
	Apllication will check for localizations of given city - Warsow
	and will save csv file in folder - C:\abc\xyz