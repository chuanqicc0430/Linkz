/**    
 * 项目名：pcas-http-container
 * 文件名：AuthService.java    
 * 版本信息：  1.0.0     
 * Copyright (c) 2015新媒传信公司- pcas 版权所有    
 * 贡献者： 赵东    邮箱 ：zhaodongyw@feinno.com
 */
package cn.net.cvtt.imps.authtoken.service;

/**
 *  date: 2015年9月6日 上午11:03:17 <br/>
 *  描述：
 *  备注：
 */
public interface AuthService {
    public void verify(String param,AuthType authType);
}
