/**    
 * 项目名：pcas-http-container
 * 文件名：VerifyResult.java    
 * 版本信息：  1.0.0     
 * Copyright (c) 2015新媒传信公司- pcas 版权所有    
 * 贡献者： 赵东    邮箱 ：zhaodongyw@feinno.com
 */
package cn.net.cvtt.imps.authtoken;

/**
 *  date: 2015年9月7日 上午11:14:46 <br/>
 *  描述：
 *  备注：
 */
public class VerifyResult {
    private ValidateResultEnum validateResultEnum;
    private boolean isPass;
    /**
     * @return the validateResultEnum
     */
    public ValidateResultEnum getValidateResultEnum() {
        return validateResultEnum;
    }
    /**
     * @param validateResultEnum the validateResultEnum to set
     */
    public void setValidateResultEnum(ValidateResultEnum validateResultEnum) {
        this.validateResultEnum = validateResultEnum;
    }
    /**
     * @return the isPass
     */
    public boolean isPass() {
        return isPass;
    }
    /**
     * @param isPass the isPass to set
     */
    public void setPass(boolean isPass) {
        this.isPass = isPass;
    }
  
    
    
}
