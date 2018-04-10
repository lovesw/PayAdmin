package com.pay.admin.action;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.jfinal.aop.Before;
import com.pay.admin.service.TurnoverService;
import com.pay.data.controller.BaseController;
import com.pay.data.entity.ScaleEntity;
import com.pay.data.entity.TurnoverEntity;
import com.pay.data.interceptors.Get;
import com.pay.data.interceptors.Post;
import com.pay.data.interceptors.Put;

import java.util.List;

/**
 * @createTime: 2018/4/9
 * @author: HingLo
 * @description: 工资管理
 */
public class TurnoverAction extends BaseController {
    private static final TurnoverService turnoverService = new TurnoverService();
    //*****************************************************按照主题分成*****************************************************//

    /**
     * 查询近6个月的主题在上个月的盈利列表
     */
    @Before(Get.class)
    public void list(Long id) {
        success(turnoverService.listService(id));
    }

    /**
     * 批量添加主题的营业额
     *
     * @param str           主题营业额的信息
     * @param cooperationId 市场的的Id
     */
    @Before(Post.class)
    public void add(String str, Long cooperationId) {
        if (StrUtil.isBlank(str) || cooperationId == null) {
            error("参数传递不正确");
        } else {
            JSONArray jsonArray = JSONUtil.parseArray(str);
            //将jsonArray转为TurnoverEntity集合
            List<TurnoverEntity> list = JSONUtil.toList(jsonArray, TurnoverEntity.class);
            boolean bool = turnoverService.addService(list, cooperationId);
            result(bool, "添加失败");
        }
    }

    /**
     * 检测该月该市场是否已经提交过
     *
     * @param cooperationId 市场Id
     */
    @Before(Get.class)
    public void check(Long cooperationId) {
        success(turnoverService.checkService(cooperationId));
    }

    /**
     * 修改指定月份的主题的营业额
     *
     * @param tid 主题的Id
     * @param num 收银额
     */
    @Before(Put.class)
    public void update(Long tid, double num) {
        if (tid == null || num < 0) {
            error("参数传递不正确");
        } else {
            boolean bool = turnoverService.updateService(tid, num);
            result(bool, "修改失败");
        }

    }

    //*****************************************************按照比例分成*****************************************************//

    /**
     * 添加比例分成
     *
     * @param str           Json格式的数据
     * @param cooperationId 合作市场
     * @param money         分配的金额
     */
    public void sAdd(String str, Long cooperationId, Double money) {

        if (StrUtil.isBlank(str) || cooperationId == null || money <= 0) {
            error("参数传递不正确");
        } else {
            JSONArray jsonArray = JSONUtil.parseArray(str);
            //将jsonArray转为TurnoverEntity集合
            List<ScaleEntity> list = JSONUtil.toList(jsonArray, ScaleEntity.class);
            boolean bool = turnoverService.sAddService(list, cooperationId, money);
            result(bool, "添加失败");
        }
    }

    /**
     * 查看指定的市场的分成信息
     */
    public void sList(Long cooperationId) {
        if (cooperationId == null) {
            error("合作市场不正确");
        } else {
            success(turnoverService.sListService(cooperationId));
        }
    }

    /**
     * 修改比例分成的信息
     *
     * @param id  唯一Id
     * @param num 修改的金额
     */
    public void sUpdate(Long id, Double num) {
        if (id == null || num == null) {
            error("修改参数不正确");
        } else {
            result(turnoverService.sUpdateService(id, num), "修改失败");
        }
    }
}
