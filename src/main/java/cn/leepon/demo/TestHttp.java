package cn.leepon.demo;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;
import com.alibaba.fastjson.JSONObject;

import cn.leepon.util.HttpClientUtil;

public class TestHttp {
	
	private static final String url = "http://member.d.pa.com//v2/service/common/aes/decode";
	
	@Test
	public void demo1(){
		
		BasicNameValuePair pair = new BasicNameValuePair("45495", "LngfatZ2ccZSxBQkIH+S/aebnn1ISAph+RyRfsxBdFO9j4WNY2BAUYrdo1VW76BG");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(pair);
		String string = HttpClientUtil.executeByPOST(url, params);
		System.err.println(string);
	}
	
	@Test
	public void demo2(){
		JSONObject json = new JSONObject();
		json.put("45495", "LngfatZ2ccZSxBQkIH+S/aebnn1ISAph+RyRfsxBdFO9j4WNY2BAUYrdo1VW76BG");
		String string = HttpClientUtil.executeByPOST(url, json);
		JSONObject jsonObject = HttpClientUtil.toJson(string);
		JSONObject json2 = jsonObject.getJSONObject("data");
		String value = json2.getString("45495");
		System.err.println(value);
	}

}
