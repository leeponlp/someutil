package cn.leepon.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import cn.leepon.util.HtmlWordToStrUtil;

public class HtmlWordToStrFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		 HttpServletRequest request = (HttpServletRequest) req;
	     HttpServletResponse response = (HttpServletResponse) res;
	     HtmlCharacterRequest myRequest = new HtmlCharacterRequest(request);
	     chain.doFilter(myRequest, response);
		
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	class HtmlCharacterRequest extends HttpServletRequestWrapper{

		HttpServletRequest request;
		
		public HtmlCharacterRequest(HttpServletRequest request) {
			super(request);
			this.request = request;
		}
		
		
		@Override
		public Map<String, String[]> getParameterMap() {
			// TODO Auto-generated method stub
			 @SuppressWarnings("unchecked")
			Map<String, String[]> parameterMap = request.getParameterMap();
		        for (String parameterName : parameterMap.keySet()) {
		            String[] values = parameterMap.get(parameterName);
		            if (values != null) {
		                for (int i = 0; i < values.length; i++) {
		                    try {
		                        // 转义处理
		                        values[i] = HtmlWordToStrUtil.filter(values[i]);
		                    } catch (Exception e) {
		                        e.printStackTrace();
		                    }
		                }
		            }
		        }
		        return parameterMap;
			
		}
		
		 @Override
		    public String getParameter(String name) {
		        Map<String, String[]> parameterMap = getParameterMap();
		        String[] values = parameterMap.get(name);
		        if (values == null) {
		            return null;
		        }
		        // 取回参数的第一个值
		        return values[0]; 
		    }
		 
		    @Override
		    public String[] getParameterValues(String name) {
		        Map<String, String[]> parameterMap = getParameterMap();
		        String[] values = parameterMap.get(name);
		        return values;
		    }
		
	}

}
