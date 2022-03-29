package com.xiaoliu.boot_vue.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接口统一返回包装类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private String code; //状态码
    private String msg; //异常结果
    private Object data; //携带的数据

    /**
     * 不带携带数据的成功请求
     * @return
     */
    public static Result success(){
        return new Result(Constants.CODE_200,"",null);
    }

    /**
     * 携带数据的成功请求
     * @param data
     * @return
     */
    public static Result success(Object data){
        return new Result(Constants.CODE_200,"",data);
    }

    /**
     * 存在错误的error
     * @param code 状态码
     * @param msg 异常
     * @return
     */
    public static Result error(String code,String msg){
        return new Result(code,msg,null);
    }

    /**
     * 系统异常
     * @return
     */
    public static Result error(){
        return new Result(Constants.CODE_500,"系统错误",null);
    }


}
