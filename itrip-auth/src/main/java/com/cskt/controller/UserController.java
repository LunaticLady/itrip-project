package com.cskt.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cskt.common.constants.ErrorCodeEnum;
import com.cskt.common.exception.ServiceException;
import com.cskt.common.vo.ReturnResult;
import com.cskt.entity.ItripUser;
import com.cskt.entity.condition.UserRegisterCondition;
import com.cskt.itripauth.service.UserService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 检查用户信息
     */
    @GetMapping("/ckusr")
    public ReturnResult checkUser(String name) {
        // 检查是否有用户名参数
        if (!StringUtils.hasLength(name)) {
            return ReturnResult.error(ErrorCodeEnum.AUTH_PARAMETER_IS_EMPTY);
        }
        QueryWrapper<ItripUser> qw = new QueryWrapper<>();
        qw.eq("user_code", name);
        ItripUser user = userService.getOne(qw);
        // 检查是否已存在该用户
        if (user != null) {
            return ReturnResult.error(ErrorCodeEnum.AUTH_USER_ALREADY_EXISTS);
        }
        return ReturnResult.ok();
    }

    /**
     * 邮箱注册接口
     */
    @PostMapping("/doregister")
    public ReturnResult doRegister(@RequestBody UserRegisterCondition condition) {
        if (!validEmail(condition.getUserCode())) {
            throw new ServiceException("30012", "邮箱格式错误");
        }
        boolean result = userService.userRegister(condition, "email");
        return result ? ReturnResult.ok() : ReturnResult.error();
    }

    /**
     * 手机号码注册接口
     */
    @PostMapping("/doRegisterByPhone")
    public ReturnResult doRegisterByPhone(@RequestBody UserRegisterCondition condition) {
        if (!validPhone(condition.getUserCode())) {
            throw new ServiceException("30013", "手机号码格式错误");
        }
        boolean result = userService.userRegister(condition, "email");
        return result ? ReturnResult.ok() : ReturnResult.error();
    }

    /**
     * 激活邮箱账号接口
     */
    @PutMapping("/activate")
    public ReturnResult activate(@RequestParam String user, @RequestParam String code) {
        if (!StringUtils.hasLength(user) || !StringUtils.hasLength(code)) {
            throw new ServiceException(ErrorCodeEnum.AUTH_PARAMETER_IS_EMPTY);
        }
        boolean result = userService.active(user, code);
        return result ? ReturnResult.ok() : ReturnResult.error();
    }

    /**
     * 激活手机账号接口
     */
    @PutMapping("/activateByPhone")
    public ReturnResult activateByPhone(@RequestParam String user, @RequestParam String code) {
        if (!StringUtils.hasLength(user) || !StringUtils.hasLength(code)) {
            throw new ServiceException(ErrorCodeEnum.AUTH_PARAMETER_IS_EMPTY);
        }
        boolean result = userService.validatePhone(user, code);
        return result ? ReturnResult.ok() : ReturnResult.error();
    }

    /**
     * 验证邮箱格式
     */
    private boolean validEmail(String email) {
        String regex = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
        return Pattern.compile(regex).matcher(email).find();
    }

    /**
     * 验证手机号码格式
     */
    public boolean validPhone(String phone) {
        String regex = "^1[34578]{1}\\d{9}$";
        return Pattern.compile(regex).matcher(phone).find();
    }

}
