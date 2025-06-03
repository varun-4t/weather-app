<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Weather Result</title>
  <link rel="stylesheet" href="style.css">
</head>
<body>
  <div class="container result">
    <h2>📅 Date: ${date}</h2>
    <h2>🏙️ City: ${city}</h2>
    <h2>🌡️ Temperature: ${temp} °C</h2>
    <h2>⛅ Weather: ${weather}</h2>
    <h2>💧 Humidity: ${humidity}%</h2>
  </div>
</body>
</html>
