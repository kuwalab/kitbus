package net.kuwalab.kit.kitbus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class App {
	@RequestMapping("/")
	public String home() {
		try {
			URL url = new URL(
					"http://www.kanazawa-it.ac.jp/shuttlebus/servicetable.csv");

			HttpURLConnection conn = null;

			try {
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");

				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					try (BufferedReader br = new BufferedReader(
							new InputStreamReader(conn.getInputStream(),
									Charset.forName("Windows-31J")))) {
						String line;
						while ((line = br.readLine()) != null) {
							System.out.println(line);
						}
					}
				}
			} finally {
				if (conn != null) {
					conn.disconnect();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "Hello world!";
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
