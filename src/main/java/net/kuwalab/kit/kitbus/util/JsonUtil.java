package net.kuwalab.kit.kitbus.util;

import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON関連のユーティリティ
 * 
 * @author kuwalab
 */
public abstract class JsonUtil {
	/**
	 * オブジェクトをJSON Stringに変換する。<br>
	 * 変換できなかった場合には{}を返す
	 * 
	 * @param object
	 * @return
	 */
	public static String objectToJson(Object object) {
		ObjectMapper objectMapper = new ObjectMapper();
		Optional<String> jsonString = Optional.empty();

		try {
			jsonString = Optional.of(objectMapper.writeValueAsString(object));
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return jsonString.orElse("{}");
	}
}
