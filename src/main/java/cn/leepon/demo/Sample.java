package cn.leepon.demo;
import java.nio.charset.Charset;
import java.util.Arrays;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * openApi调用示例
 *   openApi是基于http的，只要能发起http请求即可，不一定要本示例所引用的第三方jar包
 */
public class Sample {

	// token值
	private static String tokenCache = "";

	// clientId在 平安开放平台 我的应用列表里查看，格式以P_ 开头
	private static final String clientId = "P_yyayy_zt";

	// 应用密码
	private static final String clientPassword = "6Q6qgxb1";

	static {
		getAccessToken();// 初始化token
	}

	/**
	* 调用示例： 第一步，初始化token（在token失效期内只需获取一次，不要每次调用都获取，失效期内获取再多次也是同样的token值）
	* 第二步，调用open api （注：如果调用结果出现token失效，那么就重新获取此token再调用。）
	* 
	* @param args
	* @throws JSONException 
	*/
	public static void main(String[] args)  {
		
		for(int i =0;i < 100;i++) {
		String resultStr=invoke();
		System.out.println("请求open APi "+i+"返回结果:" + resultStr);
		}
       
		
		
		
		
//		 JSONObject result = JSON.parseObject(resultStr);// 调用open api
		/*String code = result.getString("ret");// 如果OPEN API选择包装，那么就会有类似{'ret':,'msg':,'data':} ret是状态编码 为0代表正常
		// 13002:非法的access_token
		// 13012:已失效的access_token
		// !!重要!! 必须判断access_token是否失效，失效了要重新获取一次token再调用
		if ("13002".equals(code) || "13012".equals(code)) {
			// 如果token失效了，重新获取一下token再调
			getAccessToken();
			System.out.println("第二次请求 返回结果:" + invoke());
		}*/

	}

	/**
	* 发起Open Api的调用
	* 
	* @return  调用返回的结果
	*/
	private static String invoke() {
		RestTemplate restTemplate = new RestTemplate();
		StringBuffer url = new StringBuffer();
		url.append("http://api.pingan.com.cn/open/um_search/authenticate.do");// 其中 /appsvr/group/getApproveFlow/asd是openApi的uri
		url.append("?access_token=29CC71211881484ABC4CAEF51ADBBBA5");
		url.append("&request_id=CHI_" + System.currentTimeMillis());
		url.append("&ciphertext=fsaj2423421412cxvsfgdsjgksdgfdsjklfsajfkdasfjk");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		//有了这句话就可以使返回结果转为json格式(data参数里就没有双引号)
		headers.setAccept(Arrays.asList(new MediaType[] { new MediaType("application", "json", Charset.forName("UTF-8"))}));
		headers.add("Proxy-Connection", "Keep-Alive");
		
		// 接口的业务逻辑的参数,例如studentName.(也就是你传给服务提供方的参数)
		HttpEntity<String> requestEntity = new HttpEntity<String>(null, headers);
		// 注意：openApi要求以get方式提交，就必须get提交，如果要求以post方式提交就必须以post方式提交。
		ResponseEntity<String> exchange = restTemplate.exchange(url.toString(),HttpMethod.POST, requestEntity, String.class);
		return exchange.getBody();

	}

	/**
	* 获取access_token
	* @throws JSONException 
	*/
	private static void getAccessToken() {
		RestTemplate restTemplate = new RestTemplate();
		
		//拼url
		StringBuffer url = new StringBuffer();
		url.append("http://esg-oauth-stg.paic.com/oauth/oauth2/access_token");
		url.append("?client_id=" + clientId);
		url.append("&grant_type=client_credentials");
		url.append("&client_secret=" + clientPassword);
		
		//发起请求
		String tokenResult = restTemplate.getForObject(url.toString(),String.class);
		System.out.println("获取token的返回结果:" + tokenResult);


//		JSONObject result = new JSONObject(tokenResult);
		JSONObject result = JSON.parseObject(tokenResult);
		tokenCache = result.getJSONObject("data").getString("access_token");
		System.out.println("新的token值:" + tokenCache);
	}

}
