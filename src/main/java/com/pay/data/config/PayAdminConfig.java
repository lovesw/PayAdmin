package com.pay.data.config;

import com.jfinal.config.*;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.template.Engine;
import com.pay.admin.config.AdminRoutes;
import com.pay.data.interceptors.ExceptionInterceptor;
import com.pay.staff.config.StaffRoutes;
import com.pay.sys.config.SysRoutes;
import com.pay.user.config.LoginRoutes;
import com.pay.user.config.UserRoutes;
import com.pay.user.model._MappingKit;
import lombok.extern.slf4j.Slf4j;
import org.beetl.ext.jfinal3.JFinal3BeetlRenderFactory;

/**
 * @createTime: 2018/2/8
 * @author: HingLo
 * @description: 配置文件
 */
@Slf4j
public class PayAdminConfig extends JFinalConfig {
    /**
     * 此方法用来配置JFinal常量值，如开发模式常量devMode的配置，如下代码配置了JFinal运行在开发模式：
     *
     * @param constants
     */
    @Override
    public void configConstant(Constants constants) {
        //设置是否是开发者模式
        constants.setDevMode(true);

        //beet sql整合
        JFinal3BeetlRenderFactory rf = new JFinal3BeetlRenderFactory();
        rf.config();
        constants.setRenderFactory(rf);
    }

    @Override
    public void configRoute(Routes routes) {
        //仅仅是登录拦截的url
        routes.add(new LoginRoutes());
        //user包下的URL
        routes.add(new UserRoutes());
        //admin包下的URL
        routes.add(new AdminRoutes());
        //sys包下的URL
        routes.add(new SysRoutes());
        //staff下的url
        routes.add(new StaffRoutes());

    }

    @Override
    public void configEngine(Engine engine) {

    }

    /**
     * 插件配置
     *
     * @param plugins
     */
    @Override
    public void configPlugin(Plugins plugins) {
        //加载配置文件
        Prop prop = PropKit.use("db.properties");
        //配置数据库连接池
        DruidPlugin druidPlugin = new DruidPlugin(prop.get("jdbcUrl"), prop.get("user"), prop.get("password").trim());
        plugins.add(druidPlugin);
        //添加模型与表的映射
        ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(druidPlugin);
        //设置数据方言
        activeRecordPlugin.setDialect(new MysqlDialect());
        //设置显示sql语句
        activeRecordPlugin.setShowSql(true);
        //应用插件
        plugins.add(activeRecordPlugin);
        //添加缓存插件使用
        plugins.add(new EhCachePlugin());
        //添加表与实体的对应关系
        _MappingKit.mapping(activeRecordPlugin);

    }

    @Override
    public void configInterceptor(Interceptors interceptors) {
        //添加全局异常处理拦截器
        interceptors.add(new ExceptionInterceptor());
    }

    @Override
    public void configHandler(Handlers handlers) {

    }

    /**
     * 系统启动完成后回调
     */
    @Override
    public void afterJFinalStart() {
        log.info("系统启动成功");

    }

    /***
     *  系统关闭之前回调
     */
    @Override
    public void beforeJFinalStop() {
        log.info("系统准备关闭");
    }
}
