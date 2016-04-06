package cn.leepon.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

public class JacksonUtil {

	/**
	 * 基于jackson工具包的将json串转换成list对象
	 * @param str
	 * @param clazz
	 * @return
	 * @throws JsonParseException
	 * @throws IOException
	 */
	public static <T> List<T> getList(String str, Class<T> clazz) throws JsonParseException, IOException {

		List<T> list = new ArrayList<T>();
		// 判断
		if (StringUtils.isNotEmpty(str)) {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonParser parser = objectMapper.getJsonFactory().createJsonParser(str);
			JsonNode nodes = parser.readValueAsTree();
			// 遍历树
			for (JsonNode node : nodes) {
				list.add(objectMapper.readValue(node, clazz));
			}
		}
		return list;
	}

}
