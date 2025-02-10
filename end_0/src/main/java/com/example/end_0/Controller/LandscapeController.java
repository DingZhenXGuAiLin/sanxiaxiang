package com.example.end_0.Controller;

import com.example.end_0.Common.Result.Result;
import com.example.end_0.Pojo.entity.Landscape;
import com.example.end_0.Service.LandscapeService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/landscape")
public class LandscapeController {

    @Autowired
    private LandscapeService landscapeService;

    @PostMapping("/add")
    public Result add(@RequestParam String name) {
        if(landscapeService.getIdByName(name)!=null){return Result.error("景点名已存在");}
        landscapeService.addLandscape(name);
        return Result.success(landscapeService.getIdByName(name));
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam Integer landscape_id) {
        landscapeService.deleteLandscape(landscape_id);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Landscape landscape) {
        landscapeService.updateLandscape(landscape);
        return Result.success();
    }

    @GetMapping("/getById")
    public Result<Landscape> getById(@RequestParam Integer landscape_id) {
        System.out.println(landscapeService.getLandscape(landscape_id));
        return Result.success(landscapeService.getLandscape(landscape_id));
    }


    @GetMapping("/getAll")
    public Result<List<Landscape>> getAll() {
        return Result.success(landscapeService.getAllLandscape());
    }

    @GetMapping("/getScoreOfLandscape")
    public Result getScoreOfLandscape(@RequestParam Integer landscape_id){return Result.success(landscapeService.getScoreOfLandscape(landscape_id));}
}