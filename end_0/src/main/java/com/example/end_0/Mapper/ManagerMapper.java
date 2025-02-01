package com.example.end_0.Mapper;

import org.apache.ibatis.annotations.*;
import com.example.end_0.Pojo.entity.Manager;
import java.util.List;

@Mapper
public interface ManagerMapper {
    @Insert("insert into manager (manager_name,manager_password) " +
            "values (#{manager_name},#{manager_password})")
    void addManager(String manager_name,String manager_password);

    @Delete("delete from manager where manager_id=#{manager_id}")
    void deleteManager(Integer manager_id);

    @Update("update manager set manager_name=#{manager_name},manager_password=#{manager_password} " +
            "where manager_id=#{manager_id}")
    void updateManager(Manager manager);

    @Select("select * from manager")
    List<Manager> getAllManager();

    @Select("select * from manager where manager_id=#{manager_id}")
    Manager getManager(Integer manager_id);
}
