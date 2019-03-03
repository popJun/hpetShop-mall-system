package com.hpetshop.sso.service.impl;

import com.hpetshop.mapper.HpUserMapper;
import com.hpetshop.pojo.HpUser;
import com.hpetshop.pojo.HpUserExample;
import com.hpetshop.sso.dao.RedisClientDAO;
import com.hpetshop.sso.service.UserService;
import com.hpetshop.utils.CookieUtils;
import com.hpetshop.utils.JsonUtils;
import com.hpetshop.utils.Result;
import com.hpetshop.utils.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ProjectName: hpetshop-parent
 * @Package: com.hpetshop.sso.service.impl
 * @Author: wushaochuan
 * @CreateDate: 2018/4/23 19:32
 * @UpdateUser:
 * @UpdateDate: 2018/4/23 19:32
 * @Version: 1.0
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private HpUserMapper hpUserMapper;
    @Autowired
    private RedisClientDAO redisClientDAO;
    @Value("${REDIS_USER_SESSION}")
    private String REDIS_USER_SESSION;//基础key
    @Value("${REDIS_EXPIRE_TIME}")
    private Integer REDIS_EXPIRE_TIME;//过期时间

    /**
     * 用于登录验证并生成token，把用户信息存入redis
     *
     * @param username, password
     * @return com.hpetshop.utils.Result
     * @author wushaochuan
     * @date 2018/4/23 21:58
     */
    @Override
    public Result getUser(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        HpUserExample example = new HpUserExample();
        HpUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        criteria.andPasswordEqualTo(DigestUtils.md5DigestAsHex(password.getBytes()));
        List<HpUser> list = hpUserMapper.selectByExample(example);
        if (null == list || list.size() == 0) {
            return Result.error(400, "用户名或者密码错误");
        }
        HpUser user = list.get(0);
        if (user == null) {
            return Result.error(400, "用户名或者密码错误");
        }
        //生成唯一token：redis中的key
        String token = UUID.randomUUID().toString();
        //把用户信息写入redis
        //去除用户密码
        user.setPassword(null);
        redisClientDAO.set(REDIS_USER_SESSION + ":" + token, JsonUtils.objectToJson(user));
        redisClientDAO.expire(REDIS_USER_SESSION + ":" + token, REDIS_EXPIRE_TIME);
        //填写cookie，关闭浏览器失效
        CookieUtils.setCookie(request, response, "HP_TOKEN", token);
        return Result.ok(token);

    }

    /**
     * 通过token获取登录信息
     *
     * @author wushaochuan
     * @date 2018/4/24 21:45
     */
    @Override
    public Result getUserByToken(String token) {
        //从redis中取出值
        String json = redisClientDAO.get(REDIS_USER_SESSION + ":" + token);
        if (StringUtils.isEmpty(json)) {
            return Result.error(400, "此session已经过期");
        }
        redisClientDAO.expire(REDIS_USER_SESSION + ":" + token, REDIS_EXPIRE_TIME);
        return Result.ok(JsonUtils.jsonToPojo(json, HpUser.class));
    }

    /**
     * 用于安全退出
     *
     * @return com.hpetshop.utils.Result
     * @author wushaochuan
     * @date 2018/4/25 18:02
     */
    @Override
    public Result logoutByToken(HttpServletRequest request, HttpServletResponse response, String token) {
        long del = redisClientDAO.del(REDIS_USER_SESSION + ":" + token);
        CookieUtils.deleteCookie(request, response, "HP_TOKEN");
        if (del == 1) {
            return Result.ok();
        }
        return Result.error(400);
    }

    /**
     * 检查数据是否可用
     *
     * @param content, type
     * @return com.hpetshop.utils.Result
     * @author wushaochuan
     * @date 2018/4/23 19:36
     */
    @Override
    public Result checkUser(String content, Integer type) {
        HpUserExample example = new HpUserExample();
        HpUserExample.Criteria criteria = example.createCriteria();
        if (type == 1) {
            criteria.andUsernameEqualTo(content);
        } else if (type == 2) {
            criteria.andPhoneEqualTo(content);
        } else if (type == 3) {
            criteria.andEmailEqualTo(content);
        }
        List<HpUser> list = hpUserMapper.selectByExample(example);
        if (null == list || list.size() == 0) {
            return Result.ok(true);
        } else {
            return Result.ok(false);
        }
    }

    /**
     * 用于注册
     *
     * @return com.hpetshop.utils.Result
     * @author wushaochuan
     * @date 2018/4/23 21:32
     */
    @Override
    public Result saveUser(HpUser hpUser) {
        hpUser.setUpdated(new Date());
        hpUser.setCreated(new Date());
        //Spring框架的MD5Digest MD5加密
        hpUser.setPassword(DigestUtils.md5DigestAsHex(hpUser.getPassword().getBytes()));
        hpUserMapper.insert(hpUser);
        return Result.ok();
    }
}
