package com.zjx.config;

/**
 * @Description
 * @Author Carson Cheng
 * @Date 2019/8/20 17:43
 * @Version V1.0
 **/
public class Function {

    private String sqlType; // sql类型 select inster delete update
    private String funcName; // 方法名
    private String sql; // sql语句
    private String parameterType;   // 参数类型
    private Object resultType;  // 返回值类型

    public String getSqlType() {
        return sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public Object getResultType() {
        return resultType;
    }

    public void setResultType(Object resultType) {
        this.resultType = resultType;
    }
}
