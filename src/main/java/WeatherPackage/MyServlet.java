package WeatherPackage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.im.InputContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class MyServlet
 */
@WebServlet("/myservlet")
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		// get the city from the input
		String cityName = request.getParameter("cityName");
		
		//API setup
		String api = System.getenv("OPENWEATHER_API");
		if (api == null || api.isEmpty()) {
		    throw new ServletException("API key not found in environment variable: OPENWEATHER_API");
		}
		//create the url for OpenWeatherMap api request
		String APIurl = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + api ;
 		
		//API integration
		URL url = new URL(APIurl); //APIurl is a string, so converting string to URL
		HttpURLConnection connection= (HttpURLConnection) url.openConnection(); // establishing https connection
		connection.setRequestMethod("GET"); // GET means fetching data 
		
		//Reading the data from network
		InputStream inputStream = connection.getInputStream(); // getting the data in binary form
		InputStreamReader reader = new InputStreamReader(inputStream); // converting data from binary to readable characters
		
		//Want to store in string 
		StringBuilder responseContent = new StringBuilder();
		
		//to take the input from the scanner object will create a scanner object
		Scanner sc = new Scanner(reader); //reading characters one-by-one from 'reader' variable to store in string format 
		
		while(sc.hasNext()) {
			responseContent.append(sc.nextLine()); //creating a string of fetched data
		}
		sc.close();
		
		//Typecasting data in responseContent is in string format, we need to convert that data into JSON format
		Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(responseContent.toString(), JsonObject.class); //it allows JSON data into tree model
		System.out.println(jsonObject);
		
		//Date and Time
		long dateTimeStamp = jsonObject.get("dt").getAsLong() * 1000;
		String date = new Date(dateTimeStamp).toString();
		
		//Temp
		double tempKelvin = jsonObject.getAsJsonObject("main").get("temp").getAsDouble();
		int tempCelsius = (int) (tempKelvin-273.15);
		
		//humidity
		int hum = jsonObject.getAsJsonObject("main").get("humidity").getAsInt();
		
		//Wind speed
		double wind = jsonObject.getAsJsonObject("wind").get("speed").getAsDouble();
		
		//Weather condition
		String weatherCondition = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString() ;
		
		//set the data as request attributes (for sending to the jsp page)
		request.setAttribute("date", date);
		request.setAttribute("city", cityName);
		request.setAttribute("temp", tempCelsius);
		request.setAttribute("weather", weatherCondition);
		request.setAttribute("humidity", hum);
		request.setAttribute("wind", wind);
		request.setAttribute("weatherdata", responseContent.toString());
		
		if (connection.getResponseCode() != 200) {
		    request.setAttribute("error", "City not found or API error.");
		    request.getRequestDispatcher("index.jsp").forward(request, response);
		    return;
		}
		
		connection.disconnect(); //disconnecting the connection with openAPI weather
		
		//forward the request to the weather.jsp page for rendering
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}catch (Exception e) {
        e.printStackTrace();
        // Optionally send a user-friendly error page
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to fetch weather data. Please try again later.");
    }
	} 
}