package com.example.end_0.Service;

import com.example.end_0.Pojo.entity.Manager;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ManagerService {

    void addManager(String manager_name,String manager_password);

    void deleteManager(Integer manager_id);

    void updateManager(Manager manager);

    List<Manager> getAllManager();

    Manager getManager(Integer manager_id);

    Integer getIdByName(String manager_name);
}
