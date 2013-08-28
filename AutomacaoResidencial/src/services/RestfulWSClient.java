package services;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RestfulWSClient {
	private int responseCode, timeout;
	private String responseMsg;
	private String accept;
	private String lang;
	
	private ArrayList<String[]> headers;
	
	public RestfulWSClient() {
		this.timeout = -1;
		headers = new ArrayList<>();
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public void setLanguage(String lang) {
		this.lang = lang;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * Returns the HTTP status code
	 * 
	 * @return HTTP status code of the last request (last call of
	 *         invokeOperation)
	 */
	public int getLastResponseCode() {
		return responseCode;
	}

	public String getLastResponseMsg() {
		return responseMsg;
	}

	public void addHeader(String name,String value){
		headers.add(new String[]{name,value});
	}
	
	private HttpURLConnection getConnection(String url, String httpMethod,
			String mimeType) throws MalformedURLException, IOException {
		// configures the request
		URL url_link = new URL(url);
		HttpURLConnection httpCon = (HttpURLConnection) url_link
				.openConnection();

		if (timeout < 0) {
			timeout = 15000;
		}
		// maximum time to wait for a response
		httpCon.setReadTimeout(timeout); 
		// maximum time to wait for establishing connection
		httpCon.setConnectTimeout(timeout); 
		// the HTTP request method
		httpCon.setRequestMethod(httpMethod); 
		// the content type of the message which is going to be sent
		httpCon.setRequestProperty("Content-type", mimeType); 
		if (accept != null) {
			httpCon.setRequestProperty("Accept", accept);
		}
		if (lang != null) {
			httpCon.setRequestProperty("Accept-Language", lang);
		}
		
		//set another headers
		for(String[] h: headers){
			httpCon.setRequestProperty(h[0],h[1]);
		}
		httpCon.setDoInput(true);
		return httpCon;
	}

	/**
	 * Invoke the RestFul Web Service
	 * 
	 * @param url
	 *            URL of the WS
	 * @param httpMethod
	 *            operation type(POST, GET, PUT, DELETE, ...)
	 * @param data
	 *            request content (HTTP entity)
	 * @return an String
	 * @throws MalformedURLException
	 *             se a URL não foi encontrada
	 * @throws IOException
	 *             se a requisição foi mal-sucedida
	 */
	public String invokeOperation(String url, String httpMethod, String data,
			String mimeType) throws MalformedURLException, IOException {
		HttpURLConnection httpCon = null;
		try {
			httpCon = getConnection(url, httpMethod, mimeType);
			// if the request is POST or PUT the entity has the data
			if (httpMethod.equals("POST") || httpMethod.equals("PUT")) {
				// specify that we are going to write bytes on the connection
				httpCon.setDoOutput(true); 
				// stream to write bytes on the connection
				OutputStreamWriter osw = new OutputStreamWriter(
						httpCon.getOutputStream(), "UTF-8");
				BufferedWriter bf = new BufferedWriter(osw);
				bf.write(data); // write the content
				// close the stream (the stream, not the connection!)
				bf.close(); 
			}
			// obtain the response status code and message
			httpCon.connect();
			responseCode = httpCon.getResponseCode();
			responseMsg = httpCon.getResponseMessage();

			// succesful response.
			// A succesful response always have the first number equals to"2"
			// which means a status code between 200(inclusive) and 300.
			InputStreamReader reader;
			if (responseCode >= 200 && responseCode < 300) {
				reader = new InputStreamReader(httpCon.getInputStream(),
						"UTF-8");
			} else {
				reader = new InputStreamReader(httpCon.getErrorStream(),
						"UTF-8");
			}
			// read the response
			StringBuilder response = new StringBuilder();
			char buf[] = new char[1024];
			int len;
			while ((len = reader.read(buf)) > -1) {
				response.append(buf, 0, len);
			}
			return response.toString();
		} finally {
			if (httpCon != null) {
				httpCon.disconnect();// closes the http connection
			}
		}
	}
}
