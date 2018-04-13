package com.pay.admin.action;

import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Record;
import com.pay.admin.service.TurnoverService;
import com.pay.data.controller.BaseController;
import com.pay.data.entity.ScaleEntity;
import com.pay.data.entity.TurnoverEntity;
import com.pay.data.interceptors.Get;
import com.pay.data.interceptors.Post;
import com.pay.data.interceptors.Put;
import com.pay.data.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @createTime: 2018/4/9
 * @author: HingLo
 * @description: 工资管理
 */
public class TurnoverAction extends BaseController {
    private static final TurnoverService turnoverService = Duang.duang(TurnoverService.class);
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
            //将jsonArray转为TurnoverEntity集合
            List<TurnoverEntity> list = CommonUtils.strJsonToBean(str, TurnoverEntity.class);
            boolean bool = turnoverService.addService(list, cooperationId);
            result(bool, "添加失败");
        }
    }

    /**
     * 检测该月该市场是否已经提交过(主题分成)
     *
     * @param cooperationId 市场Id
     */
    @Before(Get.class)
    public void check(Long cooperationId) {
        if (cooperationId == null) {
            error("市场不能为空");
        } else {
            success(turnoverService.checkService(cooperationId));
        }
    }

    /**
     * 检测该该公司该市场该是否已经填写过
     *
     * @param cooperationId 合作市场
     * @param companyId     公司Id
     */
    public void checks(Long cooperationId, Long companyId) {
        if (cooperationId == null || companyId == null) {
            error("查询参数不能为空");
        } else {
            success(turnoverService.checksService(companyId, cooperationId));
        }
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
    @Before(Post.class)
    public void sAdd(String str, Long companyId, Long cooperationId, Double money) {

        if (StrUtil.isBlank(str) || cooperationId == null || money <= 0 || companyId == null) {
            error("参数传递不正确");
        } else {
            //将jsonArray转为TurnoverEntity集合
            List<ScaleEntity> list = CommonUtils.strJsonToBean(str, ScaleEntity.class);
            boolean bool = turnoverService.sAddService(list, companyId, cooperationId, money);
            result(bool, "添加失败");
        }
    }

    /**
     * 查看指定的市场的分成信息
     */
    @Before(Get.class)
    public void sList(Long companyId, Long cooperationId) {
        if (cooperationId == null || companyId == null) {
            error("查询参数不正确");
        } else {
            success(turnoverService.sListService(companyId, cooperationId));
        }
    }

    /**
     * 修改比例分成的信息
     */
    @Before(Put.class)
    public void sUpdate(String str, Double money) {
        if (StrUtil.isBlank(str) || money == null || money <= 0) {
            error("修改参数不正确");
        } else {
            //获取json的list实体对象
            List<TurnoverEntity> list = CommonUtils.strJsonToBean(str, TurnoverEntity.class);
            //批量修改集合对象
            List<Record> recordList = new ArrayList<>(list.size());
            //比例校验
            Double num = 0.0;
            for (TurnoverEntity x : list) {
                Record record = new Record();
                record.set("id", x.getId());
                Double temp = x.getNum();
                //所占比例不能为0
                if (temp <= 0) {
                    return;
                }
                record.set("ratio", x.getNum());
                record.set("pay", money);
                recordList.add(record);
                num += temp;
            }
            if (num != 100) {
                error("分成比例不正确");
            } else {
                result(turnoverService.sUpdateService(recordList), "修改失败");
            }

        }
    }


}
