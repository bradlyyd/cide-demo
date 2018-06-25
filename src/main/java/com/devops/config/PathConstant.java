package com.devops.config;

public class PathConstant {
	/**
	 * GIT  GITHUB path CONFIG
	 */
	public static String  GIT_USER_NAME="bradlyyd";
	public static String  GIT_LOCAL_PATH_BASE="/home/git/";
	public static String  GIT_SSH_PATH="~/.ssh/config";
	public static String  GIT_GITHUB_URL="https://GITHUB.com/";
	public static String  GIT_GITHUB_AUTHOR_PRE="git@GITHUB.com";
	
	/**
	 * COMMON GIT PATH
	 */
	public static String GIT_URL=GIT_GITHUB_URL;
	public static String  GIT_AUTHOR_PRE=GIT_GITHUB_AUTHOR_PRE;
	public static String  GIT_URI=GIT_URL+GIT_USER_NAME+"/";
	public static String  GIT_GITHUB_AUTHOR_USER=GIT_AUTHOR_PRE+":"+GIT_USER_NAME+"/";
	
	/**
	 * JENKINS path CONFIG11
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
	public static String TOKEN_GITHUB="c953fc5ca52210ec3598a4281fee01ffcbfbedd5";
	/**
	 *  API URL path CONFIG
	 */
	public static String API_GITHUB_CREATE_REPO="http://api.github.com/user/repos";
	
	public static String API_GITHUB_DELETE_REPO="https://GITHUB.com/api/v5/repos/"+GIT_USER_NAME+"/";
	
	/** 
	 * COMMON API 
	 */
	public  static String API_CREATE_REPO=API_GITHUB_CREATE_REPO;
	public  static String API_DELETE_REPO=API_GITHUB_DELETE_REPO;
}

