package com.example.mybatisTest.controller;

import com.example.mybatisTest.dao.TUserMapper;
import com.example.mybatisTest.entity.TUser;
import com.example.mybatisTest.model.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/7/4 12:07
 */
@RestController
@RequestMapping("/")
public class UserController {

    private final TUserMapper userMapper;

    public UserController(TUserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @RequestMapping("/getUser/{id}")
    public Result getUser(@PathVariable Integer id) {
        Result result = new Result();
        result.setCode(200);
        result.setMessage("success");
        TUser tUser = userMapper.selectByPrimaryKey(id);
        result.setData(tUser);
        return result;
    }
//
//    public static void main(String[] args) {
////        Resources.getResourceAsStream("")
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//    }
}
