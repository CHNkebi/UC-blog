package com.ty.domain.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult<T> {
    /**
     * 成功标识
     * */
    private boolean success;
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 提示信息，如果有错误时，前端可以获取该字段进行提示
     */
    private String msg;
    /**
     * 查询到的结果数据，
     */
    private T data;

    public static ResponseResult success(Object data) {
        return new ResponseResult(true, 200, "success", data);
    }

    public static ResponseResult fail(int code, String msg) {
        return new ResponseResult(false, code, msg, null);
    }
}
