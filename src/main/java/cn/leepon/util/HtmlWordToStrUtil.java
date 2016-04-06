package cn.leepon.util;

public class HtmlWordToStrUtil {
	
	public static String filter(String message) {

        if (message == null || message.length() == 0)
            return null;
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < message.length(); i++) {
        	char c = message.charAt(i);
            switch (c) {
            case '<':
                result.append("<");
                break;
            case '>':
                result.append(">");
                break;
            case '&':
                result.append("&");
                break;
            case '"':
                result.append('"');
                break;
            default:
                result.append(message.charAt(i));
            }
        }
        return (result.toString());

    }

    public static void main(String[] args) {
        System.out.println(filter("<a>呵呵</a>"));
    }

}
