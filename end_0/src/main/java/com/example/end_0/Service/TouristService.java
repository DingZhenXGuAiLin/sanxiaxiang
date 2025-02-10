package com.example.end_0.Service;

import com.example.end_0.Common.Result.Result;
import com.example.end_0.Pojo.entity.Tourist;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TouristService {
    void addTourist(String tourist_name,String tourist_password);

    void deleteTourist(Integer tourist_id);

    void updateTourist(Tourist tourist);

    List<Tourist> getAllTourist();

    Tourist getTourist(Integer tourist_id);

    Integer getIdByName(String tourist_name);

    Result score(Integer tourist_id, Integer landscape_id, Integer score);

    Result deleteScoring(Integer tourist_id,Integer landscape_id);
}
