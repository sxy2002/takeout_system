package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
@Api(tags = "菜品相关接口")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<DishVO>> list(Long categoryId) {
        return listWithmutex(categoryId);
    }

    public Result<List<DishVO>> listWithmutex(Long categoryId) {
        // dish_<categoryID>
        String key = "dish_" + categoryId;
        String lockKey = "lock_" + categoryId;

        List<DishVO> list = (List<DishVO>) redisTemplate.opsForValue().get(key);
        if(list != null && list.size() > 0) {
            return Result.success(list);
        }
        // 命中空值
        if(list != null) {
            return Result.success();
        }

        // 缓存重建
        try {
            boolean hasLock = tryLock(lockKey);
            if(!hasLock) {
                Thread.sleep(50);
                return listWithmutex(categoryId);
            }

            Dish dish = new Dish();
            dish.setCategoryId(categoryId);
            dish.setStatus(StatusConstant.ENABLE);//查询起售中的菜品

            list = dishService.listWithFlavor(dish);
            if(list == null) {
                redisTemplate.opsForValue().set(key, "", 1L, TimeUnit.MINUTES);
            }
            else
                redisTemplate.opsForValue().set(key, list, 30L, TimeUnit.MINUTES);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            unlock(lockKey);
        }

        return Result.success(list);
    }

    private boolean tryLock(String key) {
        Boolean flag = redisTemplate.opsForValue().setIfAbsent(key, "1", 10L, TimeUnit.SECONDS);
        return flag != null && flag;
    }

    private void unlock(String key) {
        redisTemplate.delete(key);
    }

}
