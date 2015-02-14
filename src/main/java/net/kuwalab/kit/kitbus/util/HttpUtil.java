package net.kuwalab.kit.kitbus.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Optional;

public abstract class HttpUtil {
	public static Optional<String> getText(String address, String charset) {
		HttpURLConnection conn = null;
		try {
			URL url = new URL(address);

			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				try (BufferedReader br = new BufferedReader(
						new InputStreamReader(conn.getInputStream(),
								Charset.forName(charset)))) {
					StringBuilder sb = new StringBuilder();
					String line;
					while ((line = br.readLine()) != null) {
						sb.append(line).append("\n");
					}
					return Optional.of(sb.toString());
				}
			} else {
				return Optional.empty();
			}
		} catch (IOException e) {
			return Optional.empty();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}
}
