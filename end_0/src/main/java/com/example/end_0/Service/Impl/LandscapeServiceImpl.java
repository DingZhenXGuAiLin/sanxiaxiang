package com.example.end_0.Service.Impl;

import com.example.end_0.Mapper.LandscapeMapper;
import com.example.end_0.Mapper.t_lMapper;
import com.example.end_0.Pojo.entity.Landscape;
import com.example.end_0.Service.LandscapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LandscapeServiceImpl implements LandscapeService {

    @Autowired
    LandscapeMapper landscapeMapper;
    @Autowired
    t_lMapper tlMapper;

    @Override
    public void addLandscape(String name) {landscapeMapper.addLandscape(name);}

    @Override
    public void deleteLandscape(Integer landscape_id) {landscapeMapper.deleteLandscapeById(landscape_id);}

    @Override
    public void updateLandscape(Landscape landscape) {landscapeMapper.updateLandscape(landscape);}

    @Override
    public List<Landscape> getAllLandscape() {return landscapeMapper.getAllLandscape();}

    @Override
    public Landscape getLandscape(Integer landscape_id) {return landscapeMapper.getLandscapeById(landscape_id);}

    @Override
    public Integer getIdByName(String name) {return landscapeMapper.getIdByName(name);}

    @Override
    public Double getScoreOfLandscape(Integer landscape_id) {
        return tlMapper.getScoreOfLandscape(landscape_id);
    }
}
