package com.sign.in.common;

import org.apache.poi.ss.formula.functions.T;

/**
 * @author 邹松林
 * @version 1.0
 * @Title: M
 * @Description: TODO
 * @date 2024/3/28 16:36
 */
public class M {
    private int code;
    private String message;
    private T data;
    public M ok(){
        M m = new M();
        m.code=200;
        m.message="success";
        return m;
    }
    public M error(int code,String message){
        M m = new M();
        m.code=code;
        m.message=message;
        return m;
    }

}
