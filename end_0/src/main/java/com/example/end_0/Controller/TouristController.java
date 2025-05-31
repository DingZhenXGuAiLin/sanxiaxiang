package com.example.end_0.Controller;

import com.example.end_0.Common.Result.Result;
import com.example.end_0.Pojo.entity.Tourist;
import com.example.end_0.Service.TouristService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 游客管理控制器
 * 提供游客相关的REST API接口，包括注册、登录、信息管理和评分功能
 * 支持跨域访问，所有接口返回统一的Result格式
 *
 * @author system
 * @version 1.0
 * @since 2024
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/tourist")
public class TouristController {

    /**
     * 游客业务逻辑服务
     * 通过依赖注入获取服务实例
     */
    @Autowired
    TouristService touristService;

    /**
     * 游客注册接口
     * 新游客注册账户，需要提供用户名和密码
     * 会验证用户名的唯一性，如果用户名已存在则注册失败
     *
     * @param tourist_name     游客用户名，必须唯一
     * @param tourist_password 游客密码
     * @return 注册结果，成功时返回新生成的游客ID，失败时返回错误信息
     */
    @PostMapping("/add")
    public Result add(@RequestParam String tourist_name, @RequestParam String tourist_password) {
        // 检查用户名是否已存在
        if (touristService.getIdByName(tourist_name) != null) {
            return Result.error("用户名已注册");
        }
        // 添加新游客
        touristService.addTourist(tourist_name, tourist_password);
        // 返回新生成的游客ID
        return Result.success(touristService.getIdByName(tourist_name));
    }

    /**
     * 删除游客接口
     * 根据游客ID删除游客账户及相关数据
     *
     * @param tourist_id 要删除的游客ID
     * @return 删除结果，成功时返回success
     */
    @DeleteMapping("/delete")
    public Result delete(@RequestParam Integer tourist_id) {
        touristService.deleteTourist(tourist_id);
        return Result.success();
    }

    /**
     * 更新游客信息接口
     * 更新游客的个人信息，如用户名、密码、头像等
     *
     * @param tourist 包含更新信息的游客对象，必须包含tourist_id
     * @return 更新结果，成功时返回success
     */
    @PutMapping("/update")
    public Result update(@RequestBody Tourist tourist) {
        touristService.updateTourist(tourist);
        return Result.success();
    }

    /**
     * 根据ID获取游客信息接口
     * 查询指定ID的游客详细信息
     *
     * @param tourist_id 游客ID
     * @return 查询结果，成功时返回游客信息对象
     */
    @GetMapping("/getById")
    public Result<Tourist> getById(@RequestParam Integer tourist_id) {
        return Result.success(touristService.getTourist(tourist_id));
    }

    /**
     * 获取所有游客信息接口
     * 查询系统中所有注册游客的信息列表
     *
     * @return 查询结果，成功时返回游客信息列表
     */
    @GetMapping("/getAll")
    public Result<List<Tourist>> getAll() {
        return Result.success(touristService.getAllTourist());
    }

    /**
     * 游客评分接口
     * 游客对指定景点进行评分
     *
     * @param tourist_id   游客ID
     * @param landscape_id 景点ID
     * @param score        评分值，通常为1-5分
     * @return 评分结果，包含成功或失败信息
     */
    @PostMapping("/score")
    public Result score(@RequestParam Integer tourist_id, @RequestParam Integer landscape_id,
            @RequestParam Integer score) {
        return touristService.score(tourist_id, landscape_id, score);
    }

    /**
     * 删除游客评分接口
     * 删除游客对指定景点的评分记录
     *
     * @param tourist_id   游客ID
     * @param landscape_id 景点ID
     * @return 删除结果，包含成功或失败信息
     */
    @DeleteMapping("/deleteScoring")
    public Result deleteScoring(@RequestParam Integer tourist_id, @RequestParam Integer landscape_id) {
        return touristService.deleteScoring(tourist_id, landscape_id);
    }
}
