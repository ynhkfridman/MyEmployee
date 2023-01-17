
note :

1.Design to implement on Fronten: https://invis.io/X610Y7APRPB7 - not let me in to inspect

2.DataBase - mySql 
  "CREATE DATABASE demo"  (check the application.properties)
  
3.port 8080  (e.g. http://localhost:8080)  

4.user to role many to many - not used yet from rule sys'



problems :

1.error for registration

2.when leave the app at employee list - when resatrt -yoou go to same url, and need to "logout"

3.when restart - all pass word dont work any more

4.to connect to dynamic model popup - need react

5.multi languages - react

6.validation not intearctive enough



second thought :

dto pattern

the annotation - not familiar with them enough



package second;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import java.time.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import java.util.Arrays;

public class second {
	private static Sheets sheetsService;
	private static String APP_NAME = "MyDemo";
	private static String SPREADSHEET_ID="1lqmvUtwq6YNv9i0_41HNqz1STnBOU7ZeXHpB7M8OgU4";
	private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	private static final String TOKENS_DIRECTORY_PATH = "tokens";
	private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS); 
	private static List<List<Object>> values;
	
	
	private static Credential getCredentials() throws IOException, GeneralSecurityException {
		// Load client secrets.
		InputStream in = second.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		if (in == null) {
			throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
		}
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY,
				clientSecrets, SCOPES)
				.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
				.setAccessType("offline").build();
		//LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
		return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
	}
	public static Sheets getSheetsService() throws IOException, GeneralSecurityException{
		Credential credential = getCredentials();
		return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, credential).setApplicationName(APP_NAME).build();
	}
	
	 public static void main(String... args) throws IOException, GeneralSecurityException {
		 sheetsService = getSheetsService();
		 
		// whereDidIGet();
		// writeEmployee("12345");
	    readData();
	    int idRowNum = getIdRowNum("my");
	    System.out.println(idRowNum);
	 }
	 private static int getIdRowNum(String string) {
		 for(int i=0;i<values.size();i++) {
				if(values.get(0).get(0)=="num")return i;
			}
		return 0;
	}
	private static void checkIn(String Id) {
		 
	 }
	 private static void checkOut() {}
	 
	private static void writeEmployee(String Id) throws IOException {
		LocalDateTime localDateTime = LocalDateTime.now();
		
		ValueRange appendBody =  new ValueRange().setValues(Arrays.asList(Arrays.asList(Id,localDateTime,)));
		 AppendValuesResponse appendResult = sheetsService.spreadsheets().values()
				 .append(SPREADSHEET_ID, "gilion1", appendBody)
				 .setValueInputOption("USER_ENTERED")
				 .setInsertDataOption("INSERT_ROWS")
				 .setIncludeValuesInResponse(true)
				 .execute();
	}
	private static void whereDidIGet() throws IOException {
		String range = "gilion1!A1:";
		 ValueRange response = sheetsService.spreadsheets().values().get(SPREADSHEET_ID, range).execute();
		 List<List<Object>> values = response.getValues();
		 if (values == null || values.isEmpty()) {
				System.out.println("No data found.");
		}
		 
		
			System.out.println(values.size());
			System.out.println(values.get(0).size());
		
	}
	 
	private static void readData() throws IOException {
		String range = "gilion1";
		 ValueRange response = sheetsService.spreadsheets().values().get(SPREADSHEET_ID, range).execute();
		 values = response.getValues();
		 if (values == null || values.isEmpty()) {
				System.out.println("No data found.");
		}
		 
		for(List row : values) {
			System.out.println(row.get(row.size()-1));
		}
		System.err.println("here");
	}

	

}





