package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.dto.EmployeeDTO;
import com.sky.entity.Category;
import com.sky.entity.Employee;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "菜品分类相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增菜品分类
     * @param categoryDTO
     * @return
     */
    @PostMapping
    @ApiOperation("分类新增")
    public Result addCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("分类新增{}", categoryDTO);
        categoryService.addCategory(categoryDTO);
        return Result.success();
    }

    /**
     * 分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 分类启用已禁用
     * @param status
     * @param catId
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("分类启用已禁用")
    public Result enableOrDisableCategory(@PathVariable Integer status, @RequestParam("id") Long catId) {
        categoryService.enableOrDisableCategory(status, catId);
        return Result.success();
    }

    /**
     * 分类修改
     * @param categoryDTO
     * @return
     */
    @PutMapping
    @ApiOperation("分类修改")
    public Result updateCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.updateCategory(categoryDTO);
        return Result.success();
    }

//
//    /**
//     * 根据id查询员工信息
//     * @param id
//     * @return
//     */
//    @GetMapping("/{id}")
//    @ApiOperation("根据id查询员工信息")
//    public Result<Employee> getById(@PathVariable Long id) {
//        Employee employee = employeeService.getById(id);
//        return Result.success(employee);
//    }
//

}
