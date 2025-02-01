package com.example.end_0.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/*
* 自定义类型提供类型处理器
* 因为Questionnaire中的options属性需要作为参数传递给SQL语句，并且它是一个复杂类型（List<String>），
* 所以需要实现一个自定义的MyBatis类型处理器（TypeHandler）。将列表转换为数据库能理解的格式（json字符串），反之亦然。
* */

//PS：要把json和什么复杂数据类型互转，就把下面代码中的List<String>全部替换成该复杂数据类型就行，
//同时别忘了将parseJsonToList私有方法中的return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));最后两个class改成对应类型的class
//若有两个或以上TypeHandler，需要在Mapper中用@Result注解来指定为某个字段使用哪个TypeHandler，否则会报错

@MappedTypes(List.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class StringListTypeHandler extends BaseTypeHandler<List<String>> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        try {
            // 将List<String>转换为JSON字符串
            String json = objectMapper.writeValueAsString(parameter);
            ps.setString(i, json);
        } catch (JsonProcessingException e) {
            throw new SQLException("Error converting List<String> to JSON", e);
        }
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);
        return parseJsonToList(json);
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String json = rs.getString(columnIndex);
        return parseJsonToList(json);
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String json = cs.getString(columnIndex);
        return parseJsonToList(json);
    }

    private List<String> parseJsonToList(String json) throws SQLException {
        if (json == null || json.isEmpty()) {
            return null;
        }
        try {
            // 将JSON字符串反序列化为List<String>
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
        } catch (JsonProcessingException e) {
            throw new SQLException("Error converting JSON to List<String>", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
