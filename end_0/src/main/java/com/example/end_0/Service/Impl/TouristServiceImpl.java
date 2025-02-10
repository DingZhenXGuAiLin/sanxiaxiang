package com.example.end_0.Service.Impl;

import com.example.end_0.Common.Result.Result;
import com.example.end_0.Mapper.TouristMapper;
import com.example.end_0.Mapper.t_lMapper;
import com.example.end_0.Pojo.entity.Tourist;
import com.example.end_0.Service.TouristService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristServiceImpl implements TouristService {
    @Autowired
    TouristMapper touristMapper;
    @Autowired
    t_lMapper tlMapper;

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

    @Override
    public Integer getIdByName(String tourist_name) {
        return touristMapper.getIdByName(tourist_name);
    }

    @Override
    public Result score(Integer tourist_id, Integer landscape_id, Integer score) {
        if(tlMapper.findScoring(tourist_id,landscape_id)!=null)return Result.error("该用户已为该景点打过分");
        tlMapper.addScoring(tourist_id,landscape_id,score);
        return Result.success();
    }

    @Override
    public Result deleteScoring(Integer tourist_id, Integer landscape_id) {
        if(tlMapper.findScoring(tourist_id,landscape_id)==null)return Result.error("该用户未为该景点打过分");
        tlMapper.deleteScoring(tourist_id,landscape_id);
        return Result.success();
    }
}
