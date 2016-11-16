package com.yiqi6.util;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.List;

/**
 * 利用HttpClient进行post请求的工具类
 *
 * @author dengjh
 * @create 2016-11-16 16:18
 **/
public class HttpClientUtil {

    public static final String ENCODING = "UTF-8";
    public static final String CONTENT_TYPE = "text/html;charset=UTF-8";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String HTTP_METHOD_GET = "GET";
    public static final String HTTP_METHOD_POST = "POST";
    public static final String CHARSET_UTF_8 = "UTF-8";
    public static final String CHARSET_GBK = "GBK";
    public static final String CONTENT_TYPE_TEXT_HTML = "text/html";
    public static final String CONTENT_TYPE_APPLICATION_FORM = "application/x-www-form-urlencoded";

    private static Logger logger = Logger.getLogger(HttpClientUtil.class);


    /**
     * http get
     *
     * @param charSet
     *            default utf-8
     * @return
     */
    private static String get(GetMethod get, String charSet) {
        HttpClient httpClient = new HttpClient();
        int status = 0;
        InputStream is = null;

        try {
            status = httpClient.executeMethod(get);
            is = get.getResponseBodyAsStream();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e, e);
        }

        return getResponse(status, is, charSet);
    }

    public static String getRequest(String url, List<NameValuePair> list,
                                    String charSet, String contentType) {
        String encode = StringUtils.isNotEmpty(charSet) ? charSet
                : HttpClientUtil.CHARSET_UTF_8;
        String type = StringUtils.isNotEmpty(contentType) ? contentType
                : HttpClientUtil.CONTENT_TYPE_TEXT_HTML;

        String encodeUrl = url;
        try {
            encodeUrl = URIUtil.encodeQuery(url, encode);
        } catch (URIException e) {
            e.printStackTrace();
        }
        GetMethod get = new GetMethod(encodeUrl);
        get.addRequestHeader("Content-Type", type + ";charset=" + encode);

        if (list != null && !list.isEmpty()) {
            NameValuePair[] arr = new NameValuePair[list.size()];
            get.setQueryString(list.toArray(arr));
        }
        return get(get, encode);
    }

    public static String getRequest(String url, String queryString,
                                    String charSet, String contentType) {
        String encode = StringUtils.isNotEmpty(charSet) ? charSet
                : HttpClientUtil.CHARSET_UTF_8;
        String type = StringUtils.isNotEmpty(contentType) ? contentType
                : HttpClientUtil.CONTENT_TYPE_TEXT_HTML;

        String encodeUrl = url;
        try {
            encodeUrl = URIUtil.encodeQuery(url, encode);
        } catch (URIException e) {
            e.printStackTrace();
        }
        GetMethod get = new GetMethod(encodeUrl);
        get.addRequestHeader("Content-Type", type + ";charset=" + encode);

        if (StringUtils.isNotEmpty(queryString)) {
            get.setQueryString(queryString);
        }
        return get(get, encode);
    }

    private static String post(PostMethod postMethod, String charSet) {
        HttpClient httpClient = new HttpClient();
        int status = 0;
        InputStream is = null;

        try {
            status = httpClient.executeMethod(postMethod);
            logger.debug("response status: " + status);
            is = postMethod.getResponseBodyAsStream();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e, e);
        }

        return getResponse(status, is, charSet);
    }

    public static String postRequest(String url, List<NameValuePair> list,
                                     String charSet, String contentType) {
        String type = StringUtils.isNotEmpty(contentType) ? contentType
                : HttpClientUtil.CONTENT_TYPE_TEXT_HTML;

        PostMethod post = new PostMethod(url);
        post.addRequestHeader("Content-Type", type + ";charset=" + charSet);

        if (list != null && !list.isEmpty()) {
            NameValuePair[] arr = new NameValuePair[list.size()];
            post.setRequestBody(list.toArray(arr));
        }
        return post(post, charSet);
    }

    /**
     *
     * @param url
     *            请求url
     * @param requestBody
     *            请求体
     * @param charSet
     *            编码
     * @param contentType
     *            Content-Type类型，默认text/html
     * @return
     */
    public static String postRequest(String url, String requestBody,
                                     String charSet, String contentType) {
        String type = StringUtils.isNotEmpty(contentType) ? contentType
                : HttpClientUtil.CONTENT_TYPE_TEXT_HTML;

        PostMethod post = new PostMethod(url);
        post.addRequestHeader("Content-Type", type + ";charset=" + charSet);

        if (StringUtils.isNotEmpty(requestBody)) {
            post.setRequestBody(requestBody);
        }
        return post(post, charSet);
    }

    private static String getResponse(int status, InputStream is, String charSet) {
        String encode = StringUtils.isNotEmpty(charSet) ? charSet
                : HttpClientUtil.CHARSET_UTF_8;
        StringBuilder sb = new StringBuilder();
        if (status == HttpStatus.SC_OK && is != null) {
            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is, encode));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                logger.error(e, e);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e, e);
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public static String getRequest(String url, List<NameValuePair> list) {
        return request(url, HTTP_METHOD_GET, list, "");
    }

    public static String getRequest(String url, String queryString) {
        return request(url, HTTP_METHOD_GET, null, queryString);
    }

    public static String postRequest(String url, List<NameValuePair> list) {
        return request(url, HTTP_METHOD_POST, list, "");
    }

    public static String postRequest(String url, String requestBody) {
        return request(url, HTTP_METHOD_POST, null, requestBody);
    }

    public static String postHttpEntityRequest(String url, String json) throws Exception {
        return requestEntity(url, json);
    }

    private static String requestEntity(String url, String json) throws Exception {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isEmpty(url)) {
            return "";
        }
        HttpClient httpClient = new HttpClient();
        int status = 0;
        InputStream is = null;
        PostMethod post = new PostMethod(url);
        RequestEntity requestEntity = new StringRequestEntity(json,"application/json","UTF-8");
        post.setRequestEntity(requestEntity);
        try {
            status = httpClient.executeMethod(post);
            is = post.getResponseBodyAsStream();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e, e);
        }
        if (status == HttpStatus.SC_OK && is != null) {
            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is, ENCODING));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                logger.error(e, e);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e, e);
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    private static String request(String url, String method,
                                  List<NameValuePair> list, String body) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isEmpty(url)) {
            return "";
        }
        HttpClient httpClient = new HttpClient();
        int status = 0;
        InputStream is = null;
        if (method.equalsIgnoreCase("get")) {
            String encodeUrl = url;
            try {
                encodeUrl = URIUtil.encodeQuery(url, CHARSET_GBK);
            } catch (URIException e) {
                e.printStackTrace();
            }
            GetMethod get = new GetMethod(encodeUrl);
            get.addRequestHeader("Content-Type", CONTENT_TYPE_JSON);

            if (list != null && !list.isEmpty()) {
                NameValuePair[] arr = new NameValuePair[list.size()];
                get.setQueryString(list.toArray(arr));
            } else if (StringUtils.isNotEmpty(body)) {
                get.setQueryString(body);
            }
            try {
                status = httpClient.executeMethod(get);
                is = get.getResponseBodyAsStream();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e, e);
            }
        } else if (method.equalsIgnoreCase("post")) {
            PostMethod post = new PostMethod(url);
            post.addRequestHeader("Content-Type", CONTENT_TYPE_JSON);
            if (list != null && !list.isEmpty()) {
                NameValuePair[] arr = new NameValuePair[list.size()];
                post.setRequestBody(list.toArray(arr));
//				post.setRequestEntity(list.toArray(arr));
            } else if (StringUtils.isNotEmpty(body)) {
                post.setRequestBody(body);
            }
            try {
                status = httpClient.executeMethod(post);
                is = post.getResponseBodyAsStream();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e, e);
            }
        }

        if (status == HttpStatus.SC_OK && is != null) {
            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is, ENCODING));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                logger.error(e, e);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e, e);
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public static String doGetRequest(String urlstr) {
        String res = null;
        HttpClient client = new HttpClient(
                new MultiThreadedHttpConnectionManager());
//		client.getParams().setIntParameter("http.socket.timeout", 10000);
//		client.getParams().setIntParameter("http.connection.timeout", 5000);

        HttpMethod httpmethod = new GetMethod(urlstr);
        try {
            int statusCode = client.executeMethod(httpmethod);
            if (statusCode == HttpStatus.SC_OK) {
                res = httpmethod.getResponseBodyAsString();
            }
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpmethod.releaseConnection();
        }
        return res;
    }
}
