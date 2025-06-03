# 🌤️ Weather App by Varun

A simple, clean Java-based web application to display real-time weather information using the OpenWeatherMap API.

## 🚀 Features

- 🔍 Search weather by city name
- 📅 View date and time
- 🌡️ Temperature in Celsius
- 💧 Humidity
- ⛅ Weather condition
- ✅ Servlet and JSP-based dynamic web application
- 💅 Styled with CSS and emoji-enhanced results
- 🔒 API key securely handled via environment variables


## 🧪 How It Works

1. User enters a city name on the homepage.
2. Form submits a POST request to `MyServlet.java`.
3. The servlet calls the OpenWeatherMap API using `HttpURLConnection`.
4. The JSON response is parsed using GSON.
5. Weather details are passed as request attributes to `index.jsp`.
6. JSP renders a well-styled weather report using those values.

## 🔧 How to Run

### Prerequisites
- Eclipse IDE with Java EE support
- Apache Tomcat 10+
- OpenWeatherMap API key

### Setup Instructions

1. Clone the repository
2. Open project in Eclipse as a Dynamic Web Project.
3. Add the GSON jar (gson-2.x.jar) to: src/main/webapp/WEB-INF/lib/
4. Set your OpenWeatherMap API key as an environment variable:
- Variable name: OPENWEATHER_API
- Value: your_api_key
5. Start the server and run it.