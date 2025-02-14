package com.example.end_0.Mapper;

import org.apache.ibatis.annotations.*;
import com.example.end_0.Pojo.entity.Landscape;

import java.util.List;

@Mapper
public interface LandscapeMapper {
    @Insert("insert into landscape (name) values (#{name})")
    void addLandscape(String name);

    @Select("select * from landscape")
    List<Landscape> getAllLandscape();

    @Select("select * from landscape where landscape_id=#{landscape_id}")
    Landscape getLandscapeById(Integer landscape_id);

    @Update("update landscape set name=#{name},pic_url=#{pic_url},location=#{location},telephone=#{telephone},description=#{description} where landscape_id=#{landscape_id}")
    void updateLandscape(Landscape landscape);

    @Delete("delete from landscape where landscape_id=#{landscape_id}")
    void deleteLandscapeById(Integer landscape_id);

    @Select("select landscape_id from landscape where name=#{name}")
    Integer getIdByName(String name);
}
