package com.example.end_0.Controller;

import com.example.end_0.Common.Result.Result;
import com.example.end_0.Pojo.entity.Landscape;
import com.example.end_0.Service.LandscapeService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 景点管理控制器
 * 提供景点相关的REST API接口，包括景点信息的增删改查和评分查询功能
 * 支持跨域访问，所有接口返回统一的Result格式
 *
 * @author system
 * @version 1.0
 * @since 2024
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/landscape")
public class LandscapeController {

    /**
     * 景点业务逻辑服务
     * 通过依赖注入获取服务实例
     */
    @Autowired
    private LandscapeService landscapeService;

    /**
     * 添加新景点接口
     * 创建新的景点记录，需要提供景点名称
     * 会验证景点名称的唯一性，如果名称已存在则添加失败
     *
     * @param name 景点名称，必须唯一
     * @return 添加结果，成功时返回新生成的景点ID，失败时返回错误信息
     */
    @PostMapping("/add")
    public Result add(@RequestParam String name) {
        // 检查景点名称是否已存在
        if (landscapeService.getIdByName(name) != null) {
            return Result.error("景点名已存在");
        }
        // 添加新景点
        landscapeService.addLandscape(name);
        // 返回新生成的景点ID
        return Result.success(landscapeService.getIdByName(name));
    }

    /**
     * 删除景点接口
     * 根据景点ID删除景点记录及相关数据
     *
     * @param landscape_id 要删除的景点ID
     * @return 删除结果，成功时返回success
     */
    @DeleteMapping("/delete")
    public Result delete(@RequestParam Integer landscape_id) {
        landscapeService.deleteLandscape(landscape_id);
        return Result.success();
    }

    /**
     * 更新景点信息接口
     * 更新景点的详细信息，如名称、图片、位置、电话、描述等
     *
     * @param landscape 包含更新信息的景点对象，必须包含landscape_id
     * @return 更新结果，成功时返回success
     */
    @PutMapping("/update")
    public Result update(@RequestBody Landscape landscape) {
        landscapeService.updateLandscape(landscape);
        return Result.success();
    }

    /**
     * 根据ID获取景点信息接口
     * 查询指定ID的景点详细信息
     *
     * @param landscape_id 景点ID
     * @return 查询结果，成功时返回景点信息对象
     */
    @GetMapping("/getById")
    public Result<Map<String, Object>> getById(@RequestParam Integer landscape_id) {
        return Result.success(landscapeService.getLandscapeWithUrls(landscape_id));
    }

    /**
     * 获取所有景点信息接口
     * 查询系统中所有景点的信息列表
     *
     * @return 查询结果，成功时返回景点信息列表
     */
    @GetMapping("/getAll")
    public Result<List<Map<String, Object>>> getAll() {
        return Result.success(landscapeService.getAllLandscapeWithUrls());
    }

    /**
     * 获取景点评分接口
     * 查询指定景点的平均评分
     *
     * @param landscape_id 景点ID
     * @return 查询结果，成功时返回景点的平均评分
     */
    @GetMapping("/getScoreOfLandscape")
    public Result getScoreOfLandscape(@RequestParam Integer landscape_id) {
        return Result.success(landscapeService.getScoreOfLandscape(landscape_id));
    }
}