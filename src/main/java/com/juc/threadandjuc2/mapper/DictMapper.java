package com.juc.threadandjuc2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juc.threadandjuc2.entity.Dict;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author liu
 * @create 2023-09-26-22:04
 */
@Mapper
@Repository
public interface DictMapper extends BaseMapper<Dict> {
}