package com.cskt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cskt.entity.dto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
