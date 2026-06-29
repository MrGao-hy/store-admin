package com.gxh.admin.system.mapper;

import com.gxh.admin.system.entity.UserShop;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户-门店绑定表（店长、店员归属门店） Mapper 接口
 * </p>
 *
 * @author gaoxianhua
 * @since 2026-06-23
 */
@Mapper
public interface UserShopMapper extends BaseMapper<UserShop> {

}
