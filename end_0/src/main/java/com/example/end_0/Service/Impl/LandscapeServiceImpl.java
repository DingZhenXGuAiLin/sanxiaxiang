package com.example.end_0.Service.Impl;

import com.example.end_0.Mapper.LandscapeMapper;
import com.example.end_0.Pojo.entity.Landscape;
import com.example.end_0.Service.LandscapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LandscapeServiceImpl implements LandscapeService {

    @Autowired
    LandscapeMapper landscapeMapper;

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
}
