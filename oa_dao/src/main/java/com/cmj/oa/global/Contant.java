package com.cmj.oa.global;

import java.util.ArrayList;
import java.util.List;

public class Contant {
    //职务
    public static final String POST_STAFF = "报销人员";
    public static final String POST_FM = "收单员";
    public static final String POST_GM = "系统管理员";
    public static final String POST_CAHIER = "审核人员";

    public static final String POST_AUDITLEADER = "审核负责人";

    public static List<String> getGuahaoTypes(){
        List<String> list = new ArrayList<>();
        list.add("专家门诊");
        list.add("特需门诊");
        list.add("急诊");
        return list;
    }

    public static List<String> getPost(){
        List<String> list = new ArrayList<>();
        list.add(POST_STAFF);
        list.add(POST_FM);
        list.add(POST_GM);
        list.add(POST_CAHIER);

        list.add(POST_AUDITLEADER);
        return list;
    }

    //费用类别

    public static List<String> getItems(){
        List<String> list = new ArrayList<>();
        list.add("学生");
        list.add("在职人员");
        list.add("退休人员");
        list.add("离休人员");
        list.add("特殊人员");

        return list;
    }

    //报销状态
//    public static final String CLAIMVOCHER_CREATED="新创建";
//    public static final String CLAIMVOCHER_SUBMIT="已提交";
//
//    public static final String CLAIMVOCHER_APPROVED="已审核";
//    public static final String CLAIMVOCHER_BACK="已打回";
    public static final String CLAIMVOCHER_TERMINATED="已终止";
    public static final String CLAIMVOCHER_RECHECK="待复审";
//    public static final String CLAIMVOCHER_PAID="已打款";

    public static final String CLAIMVOCHER_CREATED="新创建";
    public static final String CLAIMVOCHER_SUBMIT="已提交";
    public static final String CLAIMVOCHER_UNPASSED="未通过";
    public static final String CLAIMVOCHER_APPROVED="已审核";
    public static final String CLAIMVOCHER_BACK="已打回";
    public static final String CLAIMVOCHER_PASSED="已通过";
    public static final String CLAIMVOCHER_PAID="已打款";


    //审核额度
    public static final double LIMIT_CHECK = 5000.00;

    //处理方式
//    public static final String DEAL_CREATE="创建";
//    public static final String DEAL_SUBMIT="提交";
//    public static final String DEAL_UPDATE="修改";
//    public static final String DEAL_BACK="打回";
//    public static final String DEAL_REJECT="拒绝";
//    public static final String DEAL_PASS="通过";
//    public static final String DEAL_PAID="打款";

    public static final String DEAL_CREATE="创建";
    public static final String DEAL_SUBMIT="提交";
    public static final String DEAL_UPDATE="修改";
    public static final String DEAL_APPROVE="审核";
    public static final String DEAL_BACK="打回";
    public static final String DEAL_REJECT="不通过";
    public static final String DEAL_PASS="通过";
    public static final String DEAL_PAID="打款";
}
