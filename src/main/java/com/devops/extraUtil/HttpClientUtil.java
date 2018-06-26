package com.devops.extraUtil;

import java.io.IOException;
import java.net.URI;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.devops.config.PathConstant;



/** 
 * @Description 

 */
public class HttpClientUtil {
    
    private static final Logger log = LoggerFactory.getLogger(HttpClientUtil.class);
    
    public  static String token=PathConstant.TOKEN_GITHUB;
    public  static String githubToken=PathConstant.TOKEN_GITHUB;
    public static void main(String[] args) throws Exception{
    
	
    	HttpClientUtil.replayPipeline();

	}
    
    public static void  replayPipeline() throws Exception{
    	try {
	    	HttpClientUtil hc=new HttpClientUtil();
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("stageName", "pull"));
			hc.executeByPOST("http://new.cicd.pro:8080/job/aarrrr/1/restart/restart", params);
    	}catch (Exception e) {
    		e.setStackTrace(e.getStackTrace());
    		throw e;
    	}
    }
    public static void  createGiteeRepo(String repoName) throws Exception{
    	try {
	    	HttpClientUtil hc=new HttpClientUtil();
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("name", repoName));
			params.add(new BasicNameValuePair("access_token", HttpClientUtil.token));
			hc.executeByPOST(PathConstant.API_CREATE_REPO, params);
    	}catch (Exception e) {
    		Exception e1= new  Exception ("gitee repo create error:"+e.getMessage());
    		e1.setStackTrace(e.getStackTrace());
    		throw e1;
    	}
    }
    public static void createGitHubRepo(String repoName)throws Exception{
    	
    	
    	try {
	    	HttpClientUtil hc=new HttpClientUtil();
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("name", repoName));
			params.add(new BasicNameValuePair("Authorization: token", HttpClientUtil.githubToken));
			hc.executeByPOST(PathConstant.API_GITHUB_CREATE_REPO, params);
    	}catch (Exception e) {
    		Exception e1= new  Exception ("github repo create error:"+e.getMessage());
    		e1.setStackTrace(e.getStackTrace());
    		throw e1;
    	}
    }
    
    public static void  deleteGiteeRepo(String repoName) throws Exception{
    	try {
	    	HttpClientUtil hc=new HttpClientUtil();
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("name", repoName));
			params.add(new BasicNameValuePair("owner", "szjack"));
			params.add(new BasicNameValuePair("access_token", HttpClientUtil.token));
			hc.executeByDelete(PathConstant.API_DELETE_REPO+repoName, params);
    	}catch (Exception e) {
    		Exception e1= new  Exception ("gitee repo delete error:"+e.getMessage());
    		e1.setStackTrace(e.getStackTrace());
    		throw e1;
    	}
    }
    /**
     * 鍒濆鍖朒ttpClient
     */
    private CloseableHttpClient httpClient = HttpClients.createDefault();
    
    /**
     * POST鏂瑰紡璋冪敤
     * 
     * @param url
     * @param params 鍙傛暟涓篘ameValuePair閿�煎瀵硅薄
     * @return 鍝嶅簲瀛楃涓�
     * @throws Exception 
     * @throws java.io.UnsupportedEncodingException
     */
    public void executeByPOST(String url, List<NameValuePair> params) throws Exception {
        HttpPost post = new HttpPost(url);
        String userAgent = "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36"; 
        post.setHeader("User-Agent", userAgent);

        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseJson = null;
        try {
            if (params != null) {
                post.setEntity(new UrlEncodedFormEntity(params));
            }
        
            responseJson = httpClient.execute(post, responseHandler);
            
            System.out.println("HttpClient POST璇锋眰缁撴灉锛�" + responseJson);
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
            System.out.println("HttpClient POST璇锋眰寮傚父锛�" + e.getMessage());
            throw e;
        }
        catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            httpClient.close();
        }
        return ;
    }
    
    /**
     * Get鏂瑰紡璇锋眰
     * 
     * @param url 甯﹀弬鏁板崰浣嶇鐨刄RL锛屼緥锛歨ttp://ip/User/user/center.aspx?_action=GetSimpleUserInfo&codes={0}&email={1}
     * @param params 鍙傛暟鍊兼暟缁勶紝闇�瑕佷笌url涓崰浣嶇椤哄簭瀵瑰簲
     * @return 鍝嶅簲瀛楃涓�
     * @throws Exception 
     * @throws java.io.UnsupportedEncodingException
     */
    public String executeByGET(String url, Object[] params) throws Exception {
        String messages = MessageFormat.format(url, params);
        HttpGet get = new HttpGet(messages);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseJson = null;
        try {
            responseJson = httpClient.execute(get, responseHandler);
            log.info("HttpClient GET璇锋眰缁撴灉锛�" + responseJson);
            
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
            log.error("HttpClient GET璇锋眰寮傚父锛�" + e.getMessage());
            throw e;
        }
        catch (IOException e) {
            e.printStackTrace();
            log.error("HttpClient GET璇锋眰寮傚父锛�" + e.getMessage());
            throw e;
        }
        finally {
            httpClient.close();
        }
        return responseJson;
    }
    
    
   
    /**
     * @param url
     * @return
     * @throws Exception 
     */
    public String executeByGET(String url) throws Exception {
        HttpGet get = new HttpGet(url);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseJson = null;
        try {
            responseJson = httpClient.execute(get, responseHandler);
            log.info("HttpClient GET璇锋眰缁撴灉锛�" + responseJson);
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
            log.error("HttpClient GET璇锋眰寮傚父锛�" + e.getMessage());
        }
        catch (IOException e) {
            e.printStackTrace();
            log.error("HttpClient GET璇锋眰寮傚父锛�" + e.getMessage());
        }
        finally {
            httpClient.close();
        }
        return responseJson;
    }
    
 
    /**
     * delete鏂瑰紡璋冪敤
     * 
     * @param url
     * @param params 鍙傛暟涓篘ameValuePair閿�煎瀵硅薄
     * @return 鍝嶅簲瀛楃涓�
     * @throws Exception 
     * @throws java.io.UnsupportedEncodingException
     */
    public void executeByDelete(String url, List<NameValuePair> params) throws Exception {
    	HttpDeleteWithBody httpdelete = new HttpDeleteWithBody(url);  
        String userAgent = "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36"; 
        httpdelete.setHeader("User-Agent", userAgent);

        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseJson = null;
        try {
            if (params != null) {
            	httpdelete.setEntity(new UrlEncodedFormEntity(params));
            }
        
            responseJson = httpClient.execute(httpdelete, responseHandler);
            
            System.out.println("HttpClient POST璇锋眰缁撴灉锛�" + responseJson);
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
            throw e;
        }
        catch (IOException e) {
           throw e;
        }
        finally {
            httpClient.close();
        }
        return ;
    }
    
    class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {
        public static final String METHOD_NAME = "DELETE";
        public String getMethod() { return METHOD_NAME; }

        public HttpDeleteWithBody(final String uri) {
            super();
            setURI(URI.create(uri));
        }
        public HttpDeleteWithBody(final URI uri) {
            super();
            setURI(uri);
        }
        public HttpDeleteWithBody() { super(); }
    }

}
