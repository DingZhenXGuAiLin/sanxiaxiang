package com.example.end_0.Controller;

import com.example.end_0.Common.Result.Result;
import com.example.end_0.Pojo.entity.Manager;
import com.example.end_0.Service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    ManagerService managerService;

    @PostMapping("/add")
    public Result add(@RequestParam String manager_name,@RequestParam String manager_password) {
        managerService.addManager(manager_name,manager_password);
        return Result.success();
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam Integer manager_id) {
        managerService.deleteManager(manager_id);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Manager manager) {
        managerService.updateManager(manager);
        return Result.success();
    }

    @GetMapping("/getById")
    public Result<Manager> getById(@RequestParam Integer id) {
        return Result.success(managerService.getManager(id));
    }


    @GetMapping("/getAll")
    public Result<List<Manager>> getAll() {
        return Result.success(managerService.getAllManager());
    }
}
