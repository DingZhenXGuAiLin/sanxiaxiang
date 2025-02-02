package com.example.end_0.Controller;

import com.example.end_0.Common.Result.Result;
import com.example.end_0.Pojo.entity.Tourist;
import com.example.end_0.Service.TouristService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/tourist")
public class TouristController {
    @Autowired
    TouristService touristService;

    @PostMapping("/add")
    public Result add(@RequestParam String tourist_name, @RequestParam String tourist_password) {
        if(touristService.getIdByName(tourist_name)!=null){return Result.error("用户名已注册");}
        touristService.addTourist(tourist_name,tourist_password);
        return Result.success();
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam Integer tourist_id) {
        touristService.deleteTourist(tourist_id);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Tourist tourist) {
        touristService.updateTourist(tourist);
        return Result.success();
    }

    @GetMapping("/getById")
    public Result<Tourist> getById(@RequestParam Integer tourist_id) {
        return Result.success(touristService.getTourist(tourist_id));
    }


    @GetMapping("/getAll")
    public Result<List<Tourist>> getAll() {
        return Result.success(touristService.getAllTourist());
    }
}
