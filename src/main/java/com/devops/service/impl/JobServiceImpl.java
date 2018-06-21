package com.devops.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.devops.config.PathConstant;
import com.devops.entity.JenkinsJob;
import com.devops.entity.Template;
import com.devops.extraUtil.FreemarkerUtil;
import com.devops.extraUtil.GitUtil;
import com.devops.extraUtil.HttpClientUtil;
import com.devops.extraUtil.JobUtil;
import com.devops.extraUtil.WorkFlowUtil;
import com.devops.mapper.JobMapper;
import com.devops.service.IJobService;
import com.surenpi.jenkins.client.job.BuildDetail;

/**
 *
 * job service implements class
 *
 */
@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, JenkinsJob> implements IJobService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private JobMapper jobMapper;

	@Override
	public void insertJob(JenkinsJob job, Template tpl) throws Exception {
		// TODO Auto-generated method stub

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("pipelineScript", job.getPipelineScript().replaceAll("'", "&apos;"));
		String xml = FreemarkerUtil.getConfigXml(data);
		try {
			JobUtil.createByXml(job.getJobName(), xml);
		} catch (Exception e) {
			throw e;
		}

		try {
			HttpClientUtil.createGiteeRepo(job.getJobName());
			GitUtil.cloneAndPush(tpl.getTemplateName(), job.getJobName());
		} catch (Exception e) {
			JobUtil.batchDel(job.getJobName());

			throw e;
		}

		job.setGitUrl(PathConstant.GIT_URI + "/" + job.getJobName() + ".git");
		job.setCreated(new Date());
		jobMapper.insert(job);

	}
	
	@Override
	@Transactional
	public void delete(Integer id, String jobName) throws Exception {
		// TODO Auto-generated method stub
		this.deleteById(id);
		
		try {
		HttpClientUtil.deleteGiteeRepo(jobName);
		}catch (Exception e) {

			throw e;
		}
		
		jobMapper.delete(new EntityWrapper<JenkinsJob>().addFilter("id = {0}", id));
	}


	@Override
	public void updateJob(JenkinsJob job) {
		// TODO Auto-generated method stub
		job.setUpdated(new Date());
		jobMapper.updateById(job);

	}

	

	
	@Override
	public void build(JenkinsJob job) throws Exception {
	
		
	
		job.setJobStatus(1);
		this.updateById(job);
		//this.doBuild(job);
//		if(job.getBuildNum()==null)job.setBuildNum(1);
//		job.setCurBuildNum(job.getBuildNum()+1);
		
		

	}
//@Async 
	public void doBuild(JenkinsJob job) {
		try {
			JobUtil.build(job.getJobName());
			String lastinfo=WorkFlowUtil.last(job.getJobName());
			while(lastinfo.contains("IN_PROGRESS")) {
				System.out.println(WorkFlowUtil.last(job.getJobName()))	;
				 lastinfo=WorkFlowUtil.last(job.getJobName());
				Thread.sleep(5000);
			}
		
			job.setJobStatus(2); 
			job.setLastBuildDate(new Date());
			jobMapper.updateById(job);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Page<Map<Object, Object>> selectJobList(Page<Map<Object, Object>> page, Map<String ,Object> search) {
		page.setRecords(baseMapper.selectJobList(page, search));
		return page;
	}

}