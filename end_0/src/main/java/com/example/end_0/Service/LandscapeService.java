package com.example.end_0.Service;

import com.example.end_0.Common.Result.Result;
import com.example.end_0.Pojo.entity.Landscape;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LandscapeService {

    void addLandscape(String name);

    void deleteLandscape(Integer landscape_id);

    void updateLandscape(Landscape landscape);

    List<Landscape> getAllLandscape();

    Landscape getLandscape(Integer landscape_id);

    Integer getIdByName(String name);

    Double getScoreOfLandscape(Integer landscape_id);
}
