package com.example.end_0.Service.Impl;

import com.example.end_0.Mapper.TouristMapper;
import com.example.end_0.Pojo.entity.Tourist;
import com.example.end_0.Service.TouristService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristServiceImpl implements TouristService {
    @Autowired
    TouristMapper touristMapper;

    @Override
    public void addTourist(String tourist_name, String tourist_password) {
        touristMapper.addTourist(tourist_name,tourist_password);
    }

    @Override
    public void deleteTourist(Integer tourist_id) {
        touristMapper.deleteTourist(tourist_id);
    }

    @Override
    public void updateTourist(Tourist tourist) {
        touristMapper.updateTourist(tourist);
    }

    @Override
    public List<Tourist> getAllTourist() {
        return touristMapper.getAllTourist();
    }

    @Override
    public Tourist getTourist(Integer tourist_id) {
        return touristMapper.getTourist(tourist_id);
    }
}
