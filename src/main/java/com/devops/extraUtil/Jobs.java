package com.devops.extraUtil;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.surenpi.jenkins.client.BaseManager;
import com.surenpi.jenkins.client.core.JenkinsInfo;
import com.surenpi.jenkins.client.folder.FolderJob;
import com.surenpi.jenkins.client.folder.Folders;
import com.surenpi.jenkins.client.job.BuildDetail;
import com.surenpi.jenkins.client.job.Job;
import com.surenpi.jenkins.client.job.JobDetails;
import com.surenpi.jenkins.client.util.EncodingUtils;
import com.surenpi.jenkins.client.util.UrlUtils;

import net.sf.json.JSONObject;

/**
 * You can create, update, del a job through this manager.<br/>
 * 浣犲彲浠ュJenkins鐨勪换鍔″仛鍒涘缓銆佹洿鏂般�佸垹闄ょ殑鎿嶄綔
 * @author suren
 */
public class Jobs extends BaseManager
{
    /**
     * create a job inside the folder
     * @param folder
     * @param jobName
     * @param jobXml
     * @param crumFlag
     * @throws IOException
     */
    public void create(FolderJob folder, String jobName, String jobXml, Boolean crumFlag) throws IOException
    {
        String path = UrlUtils.toBaseUrl(folder) + "createItem?name=" + EncodingUtils.encodeParam(jobName);

        getClient().postXml(path, jobXml, crumFlag);
    }
    
  

    public void create(FolderJob folderJob, String jobName, String jobXml, boolean crumFlag, boolean createFolder) throws IOException
    {
        if(createFolder)
        {
            Folders folders = new Folders();
            folders.setClient(getClient());
            boolean exists = folders.exists(folderJob.getName());

            if(!exists)
            {
                folders.create(folderJob.getName());
            }
        }

        create(folderJob, jobName, jobXml, crumFlag);
    }

    public void create(FolderJob folderJob, String jobName, String jobXml) throws IOException
    {
        String path = UrlUtils.toBaseUrl(folderJob) + "createItem?name=" + EncodingUtils.encodeParam(jobName);

        getClient().postXml(path, jobXml);
    }

    /**
     * @see #create(FolderJob, String, String, Boolean)
     * @param jobName
     * @param jobXml
     * @param crumFlag
     * @throws IOException
     */
    public void create(String jobName, String jobXml, Boolean crumFlag) throws IOException
    {
        create(null, jobName, jobXml, crumFlag);
    }

    /**
     * @see #create(String, String, Boolean)
     * @param jobName
     * @param jobXml
     * @throws IOException
     */
    public void create(String jobName, String jobXml) throws IOException
    {
        create(jobName, jobXml, isCrumb());
    }

    /**
     * copy a job from a origin to new one
     * @param originName
     * @param newName
     * @throws IOException
     */
    public void copy(String originName, String newName) throws IOException
    {
        getClient().post(String.format("/createItem?mode=copy&from=%s&name=%s",
                EncodingUtils.encodeParam(originName),
                EncodingUtils.encodeParam(newName)));
    }

    /**
     * 鏇存柊娴佹按绾�
     * @param folderJob
     * @param jobName
     * @param jobXml
     * @param crumbFlag
     * @throws IOException
     */
    public void update(FolderJob folderJob, String jobName, String jobXml, Boolean crumbFlag) throws IOException
    {
        String path = UrlUtils.toJobBaseUrl(folderJob, jobName) + "/getXml.xml";
        getClient().postXml(path, jobXml, crumbFlag);
    }

    /**
     * @see #update(FolderJob, String, String, Boolean)
     * @param jobName
     * @param jobXml
     * @param crumbFlag
     * @throws IOException
     */
    public void update(String jobName, String jobXml, Boolean crumbFlag) throws IOException
    {
        update(null, jobName, jobXml, crumbFlag);
    }

    /**
     * @see #update(String, String, Boolean)
     * @param jobName
     * @param jobXml
     * @throws IOException
     */
    public void update(String jobName, String jobXml) throws IOException
    {
        update(jobName, jobXml, isCrumb());
    }

    /**
     * delete a job
     * @param folderJob
     * @param jobName
     * @param crumbFlag
     */
    public void delete(FolderJob folderJob, String jobName, Boolean crumbFlag) throws IOException
    {
        String path = UrlUtils.toJobBaseUrl(folderJob, jobName) + "/doDelete";
        getClient().post(path, crumbFlag);
    }

    /**
     * @see #delete(FolderJob, String, Boolean)
     * @param jobName
     * @param crumbFlag
     * @throws IOException
     */
    public void delete(String jobName, Boolean crumbFlag) throws IOException
    {
        delete(null, jobName, crumbFlag);
    }

    /**
     * @see #delete(String, Boolean)
     * @param jobName
     * @throws IOException
     */
    public void delete(String jobName) throws IOException
    {
        delete(jobName, isCrumb());
    }

    /**
     * 鎵归噺鍒犻櫎浠诲姟锛坖ob锛�
     * @param prefixName
     * @return
     * @throws IOException
     */
    public int batchDel(String prefixName) throws IOException
    {
        List<Job> allJobs = getAllJobs();
        int count = 0;
        if(allJobs == null)
        {
            return count;
        }

        for(Job job : allJobs)
        {
            if(job.getName().startsWith(prefixName))
            {
                delete(job.getName());
                count++;
            }
        }

        return count;
    }

    /**
     * 閲嶅懡鍚峧ob鍚嶇О
     * @param folderJob
     * @param oldName
     * @param newName
     * @param crumbFlag
     * @throws IOException
     */
    public void rename(FolderJob folderJob, String oldName, String newName, Boolean crumbFlag)
            throws IOException
    {
        String path = UrlUtils.toJobBaseUrl(folderJob, oldName)
                + "/doRename?newName=" + EncodingUtils.encodeParam(newName);
        getClient().post(path, crumbFlag);
    }

    /**
     * @see #rename(FolderJob, String, String, Boolean)
     * @param oldName
     * @param newName
     * @param crumbFlag
     * @throws IOException
     */
    public void rename(String oldName, String newName, Boolean crumbFlag) throws IOException
    {
        rename(null, oldName, newName, crumbFlag);
    }

    /**
     * @see #rename(String, String, Boolean)
     * @param oldName
     * @param newName
     * @throws IOException
     */
    public void rename(String oldName, String newName) throws IOException
    {
        rename(oldName, newName, isCrumb());
    }

    /**
     * 鑾峰彇job鐨剎ml閰嶇疆鍐呭
     * @param folderJob
     * @param jobName
     * @return
     * @throws IOException
     */
    public String getXml(FolderJob folderJob, String jobName) throws IOException
    {
        return getClient().get(UrlUtils.toJobBaseUrl(folderJob, jobName) + "/config.xml");
    }

    /**
     * @see #getXml(FolderJob, String)
     * @param jobName
     * @return
     * @throws IOException
     */
    public String getXml(String jobName) throws IOException
    {
        return getXml(null, jobName);
    }

    /**
     * disable a job by jobName<br/>
     * 绂佺敤
     * @param jobName
     * @param crumbFlag
     * @throws IOException
     */
    public void disable(String jobName, Boolean crumbFlag) throws IOException
    {
        getClient().post("/job/" + EncodingUtils.encode(jobName) + "/disable", isCrumb());
    }

    /**
     * @see #disable(String, Boolean)
     * @param jobName
     * @throws IOException
     */
    public void disable(String jobName) throws IOException
    {
        disable(jobName, isCrumb());
    }

    /**
     * 鍚敤
     * @param jobName
     * @param crumbFlag
     * @throws IOException
     */
    public void enable(String jobName, Boolean crumbFlag) throws IOException
    {
        getClient().post("/job/" + EncodingUtils.encode(jobName) + "/enable", isCrumb());
    }

    /**
     * @see #enable(String, Boolean)
     * @param jobName
     * @throws IOException
     */
    public void enable(String jobName) throws IOException
    {
        enable(jobName, isCrumb());
    }

    /**
     * Build a job by name
     * @param jobName
     * @throws IOException
     */
    public void build(String jobName) throws IOException
    {
        getClient().post("/job/" + EncodingUtils.encode(jobName) + "/build", isCrumb());
    }
   //http://new.cicd.pro:8080/job/aarrrr/5/restart
    public void replayPipeline(String jobName,int buildNum,String nodeName)throws Exception{
	  getClient().post("/job/" + EncodingUtils.encode(jobName) +"/"+buildNum +"/restart", isCrumb());
    }

    /**
     * Build a job with params<br/>
     * 鏋勫缓鍙傛暟鍖栫殑浠诲姟
     * @param jobName job name
     * @param params param map
     * @throws IOException
     */
    public void buildWithParams(String jobName, Map<String, String> params) throws IOException
    {
        StringBuffer urlBuf = new StringBuffer();
        urlBuf.append("/job/");
        urlBuf.append(EncodingUtils.encode(jobName));
        urlBuf.append("/buildWithParameters/?1=1");

        Iterator<String> it = params.keySet().iterator();
        while(it.hasNext())
        {
            String key = it.next();
            String value = params.get(key);
            urlBuf.append("&").append(key).append("=").append(value);
        }

        getClient().post(urlBuf.toString(), isCrumb());
    }

    /**
     * stop the job by name
     * @param jobName
     * @param buildId
     * @throws IOException
     */
    public void stop(String jobName, int buildId) throws IOException
    {
        getClient().post("/job/" + EncodingUtils.encode(jobName) + "/" + buildId + "/stop", isCrumb());
    }

    public JenkinsInfo getAll() throws IOException
    {
        return getClient().get("/", JenkinsInfo.class);
    }

    /**
     * 鑾峰彇鎵�鏈夌殑浠诲姟锛坖ob锛�
     * @return
     * @throws IOException
     */
    public List<Job> getAllJobs() throws IOException
    {
        JenkinsInfo jenkinsInfo = getAll();

        return jenkinsInfo.getJobs();
    }

    public JobDetails getDetails(String jobName) throws IOException
    {
        return getClient().get("/job/" + EncodingUtils.encode(jobName), JobDetails.class);
    }

    /**
     * 鑾峰彇鏌愪竴娆℃瀯寤虹殑璇︾粏淇℃伅
     * @param jobName 浠诲姟鍚嶇О
     * @param number 鏋勫缓鍙�
     * @return 鏋勫缓璇︾粏淇℃伅
     * @throws IOException
     */
    public BuildDetail getBuildDetails(String jobName, int number) throws IOException
    {
        return getClient().get("/job/" + EncodingUtils.encode(jobName) + "/" + number, BuildDetail.class);
    }

    /**
     * 鑾峰彇鏈�鍚庝竴娆＄殑鏋勫缓淇℃伅
     * @param jobName
     * @return
     * @throws IOException
     */
    public BuildDetail getLastBuildDetails(String jobName) throws IOException
    {
        JobDetails jobDetails = getDetails(jobName);
        int lastBuildNum = jobDetails.getLastBuild().getNumber();

        return getBuildDetails(jobName, lastBuildNum);
    }

    @Override
    protected String[] getDependencyArray()
    {
        return new String[0];
    }
}