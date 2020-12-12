package com.biyou.controller;

import com.biyou.pojo.User;
import com.biyou.service.UserService;
import com.biyou.utils_entry.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@CrossOrigin
@Controller
@RequestMapping("/user")
public class RegistryController {

    @Autowired
    private UserService userService;

    @PostMapping("/registry")
    @ResponseBody
    public Result registry(@RequestBody User user) {
        userService.addUser(user);
        return new Result(true,1,"http://localhost/index.html");
    }

}
