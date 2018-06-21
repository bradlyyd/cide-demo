package com.devops.config;

public class PathGitHubConstant {
	/**
	 * GIT  GITEE path CONFIG
	 */
	public static String  GIT_USER_NAME="szjack";
	public static String  GIT_LOCAL_PATH_BASE="/home/git/";
	public static String  GIT_SSH_PATH="~/.ssh/config";
	public static String  GIT_GITEE_URL="https://gitee.com/";
	public static String  GIT_GITEE_AUTHOR_PRE="git@gitee.com";
	
	/**
	 * COMMON GIT PATH
	 */
	public static String GIT_URL=GIT_GITEE_URL;
	public static String  GIT_AUTHOR_PRE=GIT_GITEE_AUTHOR_PRE;
	public static String  GIT_URI=GIT_URL+GIT_USER_NAME+"/";
	public static String  GIT_GITEE_AUTHOR_USER=GIT_AUTHOR_PRE+":"+GIT_USER_NAME+"/";
	
	/**
	 * JENKINS path CONFIG
	 */
	public static String  JENKINS_SERVER_PATH="http://127.0.0.1:8080/";
	public static String  JENKINS_SERVER_USER="admin";
	public static String  JENKINS_SERVER_PASSWD="admin";
	
	public static String JENKINS_JOB_STATUS_ONBUILD="ONBUID";
	public static String JENKINS_JOB_STATUS_NOTBUILD="NOTBUID";
	public static String JENKINS_JOB_STATUS_FINISH="NOTBUID";
	
	/**
	 * TOKEN CONFIG
	 */
	public static String TOKEN_GITEE="5e9f2eaf9a572b54af3d6607e42a08b3";
	/**
	 *  API URL path CONFIG
	 */
	public static String API_GITEE_CREATE_REPO="https://gitee.com/api/v5/user/repos";
	
	public static String API_GITEE_DELETE_REPO="https://gitee.com/api/v5/repos/"+GIT_USER_NAME+"/";
	
	/**
	 * COMMON API 
	 */
	public  static String API_CREATE_REPO=API_GITEE_CREATE_REPO;
	public  static String API_DELETE_REPO=API_GITEE_DELETE_REPO;
}

