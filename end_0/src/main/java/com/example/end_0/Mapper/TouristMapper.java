package com.example.end_0.Mapper;

import com.example.end_0.Pojo.entity.Tourist;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TouristMapper {
    @Insert("insert into tourist (tourist_name,tourist_password) " +
            "values (#{tourist_name},#{tourist_password})")
    void addTourist(String tourist_name,String tourist_password);

    @Delete("delete from tourist where tourist_id=#{tourist_id}")
    void deleteTourist(Integer tourist_id);

    @Update("update tourist set tourist_name=#{tourist_name},tourist_password=#{tourist_password} " +
            "where tourist_id=#{tourist_id}")
    void updateTourist(Tourist tourist);

    @Select("select * from tourist")
    List<Tourist> getAllTourist();

    @Select("select * from tourist where tourist_id=#{tourist_id}")
    Tourist getTourist(Integer tourist_id);

    @Select("select tourist_id from tourist where tourist_name=#{tourist_name}")
    Integer getIdByName(String tourist_name);
}
