package com.cmj.oa.biz.impl;

import com.cmj.oa.biz.ClaimVoucherBiz;
import com.cmj.oa.dao.ClaimVoucherDao;
import com.cmj.oa.dao.ClaimVoucherItemDao;
import com.cmj.oa.dao.DealRecordDao;
import com.cmj.oa.dao.EmployeeDao;
import com.cmj.oa.entity.ClaimVoucher;
import com.cmj.oa.entity.ClaimVoucherItem;
import com.cmj.oa.entity.DealRecord;
import com.cmj.oa.entity.Employee;
import com.cmj.oa.global.Contant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("claimVoucherBiz")
public class ClaimVoucherBizImpl implements ClaimVoucherBiz {

    @Qualifier("claimVoucherDao")
    @Autowired
    private ClaimVoucherDao claimVoucherDao;
    @Qualifier("claimVoucherItemDao")
    @Autowired
    private ClaimVoucherItemDao claimVoucherItemDao;
    @Qualifier("dealRecordDao")
    @Autowired
    private DealRecordDao dealRecordDao;
    @Qualifier("employeeDao")
    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items) {
        claimVoucher.setCreateTime(new Date());
      //  claimVoucher.setNextDealSn(claimVoucher.getCreateSn());//不需要指定NextDealSn是自己了
        claimVoucher.setStatus(Contant.CLAIMVOCHER_CREATED);
        claimVoucherDao.insert(claimVoucher);

        for (ClaimVoucherItem item:items){
            item.setClaimVoucherId(claimVoucher.getId());
            claimVoucherItemDao.insert(item);
        }

    }

    @Override
    public void deleteById(int id) {
        dealRecordDao.deleteByClaimVoucher(id);
        claimVoucherItemDao.deleteByClaimVoucher(id);
        claimVoucherDao.delete(id);
    }

    @Override//理论上收单员获取特定id的报销单可以调用这个函数
    public ClaimVoucher get(int id) {
        return claimVoucherDao.select(id);
    }

    @Override
    public List<ClaimVoucherItem> getItems(int cvid) {
        return claimVoucherItemDao.selectByClaimVoucher(cvid);
    }

    @Override
    public List<DealRecord> getRecords(int cvid) {
        return dealRecordDao.selectByClaimVoucher(cvid);
    }

    @Override
    public List<ClaimVoucher> getForSelf(String sn) {
        return claimVoucherDao.selectByCreateSn(sn);
    }

    @Override
    public List<ClaimVoucher> getForDeal(String sn) {
        Employee employee = employeeDao.select(sn);
        if(employee.getPost().equals(Contant.POST_STAFF)){//报销人员
            return claimVoucherDao.selectByCreateSn(sn);
        }else if(employee.getPost().equals(Contant.POST_CAHIER)){//审核人员
            return claimVoucherDao.selectForCAHIER(sn);
        }else if(employee.getPost().equals(Contant.POST_AUDITLEADER)){//审核人负责人
            return claimVoucherDao.selectForAUDITLEADER(sn);
        }
        //收单员的获取特定id的报销单 不在这里处理
        return claimVoucherDao.selectByNextDealSn(sn);
    }//获取自身可以处理的一批报销单

    @Override
    public void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items) {
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        claimVoucher.setStatus(Contant.CLAIMVOCHER_CREATED);
        claimVoucherDao.update(claimVoucher);

        List<ClaimVoucherItem> olds = claimVoucherItemDao.selectByClaimVoucher(claimVoucher.getId());
        for (ClaimVoucherItem old:olds){
            boolean isHave = false;
            for (ClaimVoucherItem item:items){
                if(item.getId() == old.getId()){
                    isHave = true;
                    break;
                }
            }
            if(!isHave){
                claimVoucherDao.delete(old.getId());
            }
        }
        for (ClaimVoucherItem item:items){
            item.setClaimVoucherId(claimVoucher.getId());
            if (item.getId()!=null && item.getId() > 0){
                claimVoucherItemDao.update(item);
            }else{
                claimVoucherItemDao.insert(item);
            }
        }
    }

    @Override
    public void submit(int id) {

        ClaimVoucher claimVoucher = claimVoucherDao.select(id);
        Employee employee = employeeDao.select(claimVoucher.getCreateSn());

        claimVoucher.setStatus(Contant.CLAIMVOCHER_SUBMIT);
        claimVoucher.setNextDealSn(null);
       //需要给next_deal_sn赋为空值，这样可以重新交给任意审核员进行审核
//        if (!employeeDao.selectByDepartmentAndPost(employee.getDepartmentSn(),Contant.POST_FM).isEmpty()) {
//            claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(employee.getDepartmentSn(), Contant.POST_FM).get(0).getSn());
//        }
        claimVoucherDao.update(claimVoucher);

        DealRecord dealRecord = new DealRecord();
        dealRecord.setDealWay(Contant.DEAL_SUBMIT);
        dealRecord.setDealSn(employee.getSn());
        dealRecord.setClaimVoucherId(id);
        dealRecord.setDealResult(Contant.DEAL_SUBMIT);
        dealRecord.setDealTime(new Date());
        dealRecord.setComment("无");
        dealRecordDao.insert(dealRecord);

    }

    @Override//需要仔细判断每个处理方式的处理 对相应的报销单字段进行变更
    public void deal(DealRecord dealRecord) {
        ClaimVoucher claimVoucher = claimVoucherDao.select(dealRecord.getClaimVoucherId());
        Employee employee = employeeDao.select(dealRecord.getDealSn());
        dealRecord.setDealTime(new Date());
        if (dealRecord.getDealWay().equals(Contant.DEAL_APPROVE)){//审核
            claimVoucher.setStatus(Contant.CLAIMVOCHER_APPROVED);
            claimVoucher.setNextDealSn(dealRecord.getDealSn());//记录审核员

            dealRecord.setDealResult(Contant.CLAIMVOCHER_APPROVED);
        }else if(dealRecord.getDealWay().equals(Contant.DEAL_BACK)){//打回
            claimVoucher.setStatus(Contant.CLAIMVOCHER_BACK);
            claimVoucher.setNextDealSn(dealRecord.getDealSn());;//记录审核员

            dealRecord.setDealResult(Contant.CLAIMVOCHER_BACK);
        }else if(dealRecord.getDealWay().equals(Contant.DEAL_PASS)){//通过
            claimVoucher.setStatus(Contant.CLAIMVOCHER_PASSED);

            dealRecord.setDealResult(Contant.CLAIMVOCHER_PASSED);
        }else if(dealRecord.getDealWay().equals(Contant.DEAL_REJECT)){//不通过
            claimVoucher.setStatus(Contant.CLAIMVOCHER_UNPASSED);

            dealRecord.setDealResult(Contant.CLAIMVOCHER_UNPASSED);
        }else if(dealRecord.getDealWay().equals(Contant.DEAL_PAID)){//打款
            claimVoucher.setStatus(Contant.CLAIMVOCHER_PAID);
            employee.setYearTotal(employee.getYearTotal()+claimVoucher.getTotalAmount());//年度报销总金额更新
            employeeDao.update(employee);//更新数据库
            dealRecord.setDealResult(Contant.CLAIMVOCHER_PAID);
        }

        claimVoucherDao.update(claimVoucher);
        dealRecordDao.insert(dealRecord);
    }

    @Override
    public List<ClaimVoucher> fuzzyQuery(String createSn, String status, Double totalAmount, String create_time, String cause) {
        return claimVoucherDao.fuzzyQuery(createSn,status,totalAmount,create_time,cause);
    }

    @Override
    public List<ClaimVoucher> fuzzyQueryForDeal(String createSn, String nextDealsn, String status, Double totalAmount, String create_time,String cause) {
        return claimVoucherDao.fuzzyQueryForDeal(createSn,nextDealsn,status,totalAmount,create_time,cause);
    }
}
