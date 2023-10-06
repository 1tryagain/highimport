package com.juc.threadandjuc2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juc.threadandjuc2.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * @author liu
 * @create 2023-09-20-16:39
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
}