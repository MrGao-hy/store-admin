package com.gxh.admin.system.service.impl;

import com.gxh.admin.system.entity.UserShop;
import com.gxh.admin.system.mapper.UserShopMapper;
import com.gxh.admin.system.service.IUserShopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户-门店绑定表（店长、店员归属门店） 服务实现类
 * </p>
 *
 * @author gaoxianhua
 * @since 2026-06-23
 */
@Service
public class UserShopServiceImpl extends ServiceImpl<UserShopMapper, UserShop> implements IUserShopService {

}
