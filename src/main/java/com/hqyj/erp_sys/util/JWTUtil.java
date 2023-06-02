package com.hqyj.erp_sys.util;

import com.hqyj.erp_sys.entity.SysUser;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

/*
 * 用户生成JWT的工具类，添加@Component注解，让该类交给Spring管理，方便其他类中自动注入
 * */
@Component
public class JWTUtil {
    public String createToken(SysUser sysUser) {

        //1、定义token有效市场，记录生成时间，计算到期时间
        long nowMiles = System.currentTimeMillis();
        //计算到期时间 1天
        long expMiles = nowMiles + 1 * 24 * 3600 * 1000;
        //2、定义token的负载信息，一般位键值对，通常用map保存
        HashMap<String, Object> map = new HashMap<>();
        map.put("suId", sysUser.getSuId());
        map.put("suName", sysUser.getSuName());
        map.put("email", sysUser.getEmail());
        map.put("suRole", sysUser.getSuRole());

        //3、创建一个用于构造token的对象
        JwtBuilder builder = Jwts.builder();
        builder.setClaims(map)//设置负载信息
                .setIssuer("com.hqyj")//设置签发这（无要求）
                .setIssuedAt(new Date(nowMiles))//设置签发时间
                .setExpiration(new Date(expMiles))//设置到期时间
                .signWith(SignatureAlgorithm.HS256, "laocha");//设置签名算法和数字签名（至少4个字符）
        return builder.compact();
    }

    /*
     * 验证token
     * */
    public ResultData validateToken(String token) {
        Claims claims;
        //获取解析token的对象
        try {
            claims = (Jwts.parser()//获取一个用于解析token的工具对象
                    .setSigningKey("laocha")//提供签发token时的签名
                    .parseClaimsJws(token))//解析token
                    .getBody();//得到保存在token中的负载信息
        } catch (SignatureException e) {
            //吐过抛出该异常，说明签名不匹配
            return ResultData.error(1, "签名不匹配");
        } catch (MalformedJwtException e) {
            return ResultData.error(2, "token有误");
        } catch (ExpiredJwtException e) {
            return ResultData.error(3, "token过期");
        } catch (Exception e) {
            return ResultData.error(4, "其他异常");
        }
        return ResultData.ok("token验证成功", claims);
    }

}
