
## HTTP Server


This project is written in Java on MacOS.

## How to compile

Open terminal and navigate to project folder. Type "javac HTTPServer.java". Now the project is compiled.
## How to run

In terminal after project has been compiled. Type "java HTTPServer portnumber" with the port number being configurable. I use "java HTTPServer 2055". 

In a separate terminal window, navigate to project folder and type "telnet localhost portnumber" with the port number being the same number used earlier. I type "telnet localhost 2055". Then type "GET /www/html.index".

In a browser type "http://localhost:2055/www/index.html" to load the web page.

