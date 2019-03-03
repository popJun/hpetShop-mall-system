package com.hpetshop.aspect;

import com.hpetshop.pojo.HpItem;
import com.hpetshop.utils.HttpClientUtil;
import com.hpetshop.utils.JsonUtils;
import com.hpetshop.utils.Result;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * 用于solr aop异步更新
 * 作用在完成更新或修改或删除是进行面向切面的拦截并更新solr
 *
 * @ProjectName: hpetshop-parent
 * @Package: com.hpetshop.aspect
 * @Author: wushaochuan
 * @CreateDate: 2018/4/13 11:34
 * @UpdateUser:
 * @UpdateDate: 2018/4/13 11:34
 * @Version: 1.0
 **/
@Aspect
@Component
public class ItemAspect {
    @Value("${SOLR_BASE_URL}")
    private String SOLR_BASE_URL;// 用于索引库同步调用的基础url
    @Value("${SOLR_UPLOADORSAVE_URL}")
    private String SOLR_UPLOADORSAVE_URL;// 更新或者保存调用的URL
    @Value("${SOLR_DEL_URL}")
    private String SOLR_DEL_URL;// 用于删除调用的url
    private static Logger logger = Logger.getLogger(ItemAspect.class);

    /**
     * 指定更新点
     *
     * @return void
     * @author wushaochuan
     * @date 2018/4/13 11:43
     */
    @Pointcut("execution(* com.hpetshop.controller.ItemController.updateItem(..)) || execution(* com.hpetshop.controller.ItemController.saveItem(..)) || execution(* com.hpetshop.controller.ItemController.updateItemReshelf(..))")
    public void updateAndSave() {

    }

    /**
     * 指定删除点
     *
     * @return void
     * @author wushaochuan
     * @date 2018/4/13 12:20
     */
    @Pointcut("execution(* com.hpetshop.controller.ItemController.deleteItem(..)) || execution(* com.hpetshop.controller.ItemController.updateItemInstock(..))")
    public void del() {

    }

    @AfterReturning(returning = "object", pointcut = "updateAndSave()")
    public void updateAfterReturning(JoinPoint joinPoint, Object object) {
        HpItem hpItem = (HpItem) ((Result) object).getData();
        updateSolr(hpItem);
    }

    @AfterReturning(returning = "object", pointcut = "del()")
    public void delAferReturning(JoinPoint joinPoint, Object object) {
        String hpItemId = (String) ((Result) object).getData();
        String[] ids = hpItemId.split(",");
        for (String id : ids) {
            delSolr(Long.parseLong(id));
        }
    }


    /**
     * 用于更新索引库 --添加和更新
     */
    private void updateSolr(HpItem hpItem) {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append(SOLR_BASE_URL).append(SOLR_UPLOADORSAVE_URL);
            String hpItemJson = JsonUtils.objectToJson(hpItem);
            String json = HttpClientUtil.doPostJson(sb.toString(), hpItemJson);
            logger.info("返回的json为" + json);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 用于更新索引库 --删除
     */
    private void delSolr(Long itemId) {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append(SOLR_BASE_URL).append(SOLR_DEL_URL).append("/").append(String.valueOf(itemId));
            String json = HttpClientUtil.doGet(sb.toString());
            logger.info("返回的json为" + json);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
