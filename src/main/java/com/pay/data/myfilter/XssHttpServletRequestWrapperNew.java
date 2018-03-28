package com.pay.data.myfilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.regex.Pattern;

/**
 * @createTime: 2018/1/4
 * @author: HingLo
 * @description: 定义xss攻击具体实现
 */
public class XssHttpServletRequestWrapperNew extends HttpServletRequestWrapper {

    private static Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
    private static Pattern scriptPattern1 = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    private static Pattern scriptPattern2 = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
            | Pattern.DOTALL);
    private static Pattern scriptPattern3 = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
    private static Pattern scriptPattern4 = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
            | Pattern.DOTALL);
    private static Pattern scriptPattern5 = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
            | Pattern.DOTALL);
    private static Pattern scriptPattern6 = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
            | Pattern.DOTALL);
    private static Pattern scriptPattern7 = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
    private static Pattern scriptPattern8 = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
    private static Pattern scriptPattern9 = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
            | Pattern.DOTALL);
    private static Pattern scriptPattern10 = Pattern.compile("<iframe>(.*?)</iframe>", Pattern.CASE_INSENSITIVE);
    private static Pattern scriptPattern11 = Pattern.compile("</iframe>", Pattern.CASE_INSENSITIVE);
    private static Pattern scriptPattern12 = Pattern.compile("<iframe(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
            | Pattern.DOTALL);


    HttpServletRequest orgRequest = null;

    XssHttpServletRequestWrapperNew(HttpServletRequest request) {
        super(request);
        orgRequest = request;
    }

    /**
     * 对一些特殊字符进行转义
     */
    private static String htmlEncode(String aText) {
        final StringBuilder result = new StringBuilder();
        final StringCharacterIterator iterator = new StringCharacterIterator(
                aText);
        char character = iterator.current();
        while (character != CharacterIterator.DONE) {
            if (character == '<') {
                result.append("<");
            } else if (character == '>') {
                result.append(">");
            } else if (character == '&') {
                result.append("&");
            } else if (character == '\"') {
                result.append("");
            } else {
                // the char is not a special one
                // add it to the result as is
                result.append(character);
            }
            character = iterator.next();
        }
        return result.toString();
    }

    /**
     * 将容易引起xss漏洞的半角字符直接替换成全角字符
     * 目前xssProject对注入代码要求是必须开始标签和结束标签(如<script></script>)正确匹配才能解析，否则报错；因此只能替换调xssProject换为自定义实现
     *
     * @param s
     * @return
     */
    private static String xssEncode(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }

        String result = stripXSS(s);
        if (null != result) {
            result = escape(result);
        }

        return result;

    }

    private static String escape(String s) {
        StringBuilder sb = new StringBuilder(s.length() + 16);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '>':
                    // 全角大于号
                    sb.append('＞');
                    break;
                case '<':
                    // 全角小于号
                    sb.append('＜');
                    break;
                case '\'':
                    // 全角单引号
                    sb.append('‘');
                    break;
                case '\"':
                    // 全角双引号
                    sb.append('“');
                    break;
                case '\\':
                    // 全角斜线
                    sb.append('＼');

                    break;
                case '%':
                    // 全角冒号
                    sb.append('％');
                    break;
                default:
                    sb.append(c);
                    break;
            }

        }
        return sb.toString();
    }

    private static String stripXSS(String value) {

        if (value != null) {
            // Avoid null characters
            value = value.replaceAll("", "");
            // Avoid anything between script tags
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid anything in a src='...' type of expression
            value = scriptPattern1.matcher(value).replaceAll("");
            value = scriptPattern2.matcher(value).replaceAll("");
            // Remove any lonesome </script> tag
            value = scriptPattern3.matcher(value).replaceAll("");
            // Remove any lonesome <script ...> tag
            value = scriptPattern4.matcher(value).replaceAll("");
            // Avoid eval(...) expressions
            value = scriptPattern5.matcher(value).replaceAll("");
            // Avoid expression(...) expressions
            value = scriptPattern6.matcher(value).replaceAll("");
            // Avoid javascript:... expressions
            value = scriptPattern7.matcher(value).replaceAll("");
            // Avoid vbscript:... expressions
            value = scriptPattern8.matcher(value).replaceAll("");
            // Avoid onload= expressions
            value = scriptPattern9.matcher(value).replaceAll("");

            value = scriptPattern10.matcher(value).replaceAll("");

            value = scriptPattern11.matcher(value).replaceAll("");
            // Remove any lonesome <script ...> tag
            value = scriptPattern12.matcher(value).replaceAll("");
        }
        return value;
    }

    /**
     * 获取最原始的request的静态方法
     *
     * @return
     */
    public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
        if (req instanceof XssHttpServletRequestWrapperNew) {
            return ((XssHttpServletRequestWrapperNew) req).getOrgRequest();
        }

        return req;
    }

    /**
     * 覆盖getParameter方法，将参数名和参数值都做xss过滤。
     * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取
     * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
     */
    @Override
    public String getParameter(String name) {
        String value = super.getParameter(xssEncode(name));
        if (value != null) {
            value = xssEncode(value);
            value = htmlEncode(value);
        }
        return value;
    }

    /**
     * 覆盖getHeader方法，将参数名和参数值都做xss过滤。 如果需要获得原始的值，则通过super.getHeaders(name)来获取
     * getHeaderNames 也可能需要覆盖
     */
    @Override
    public String getHeader(String name) {

        String value = super.getHeader(xssEncode(name));
        if (value != null) {
            value = xssEncode(value);
        }
        return value;
    }

    /**
     * 获取最原始的request
     *
     * @return
     */
    private HttpServletRequest getOrgRequest() {
        return orgRequest;
    }

}