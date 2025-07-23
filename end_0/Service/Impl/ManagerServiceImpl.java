package com.example.end_0.Service.Impl;

import com.example.end_0.Mapper.ManagerMapper;
import com.example.end_0.Pojo.entity.Manager;
import com.example.end_0.Service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    ManagerMapper managerMapper;

    @Override
    public void addManager(String manager_name, String manager_password) {
        managerMapper.addManager(manager_name,manager_password);
    }

    @Override
    public void deleteManager(Integer manager_id) {
        managerMapper.deleteManager(manager_id);
    }

    @Override
    public void updateManager(Manager manager) {
        managerMapper.updateManager(manager);
    }

    @Override
    public List<Manager> getAllManager() {
        return managerMapper.getAllManager();
    }

    @Override
    public Manager getManager(Integer manager_id) {
        return managerMapper.getManager(manager_id);
    }

    @Override
    public Integer getIdByName(String manager_name) {
        return managerMapper.getIdByName(manager_name);
    }
}
