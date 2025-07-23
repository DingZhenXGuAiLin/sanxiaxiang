package com.example.end_0.Mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface t_lMapper {
    @Insert("insert into t_l (tourist_id,landscape_id,score)" +
            "values (#{tourist_id},#{landscape_id},#{score})")
    void addScoring(Integer tourist_id,Integer landscape_id,Integer score);

    @Select("select score from t_l where tourist_id=#{tourist_id} and landscape_id=#{landscape_id}")
    Integer findScoring(Integer tourist_id,Integer landscape_id);

    @Delete("delete from t_l where tourist_id=#{tourist_id} and landscape_id=#{landscape_id}")
    void deleteScoring(Integer tourist_id,Integer landscape_id);

    @Select("select avg(score) from t_l where landscape_id=#{landscape_id}")
    Double getScoreOfLandscape(Integer landscape_id);
}
