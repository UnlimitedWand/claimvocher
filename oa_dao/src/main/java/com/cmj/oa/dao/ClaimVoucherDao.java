package com.cmj.oa.dao;

import com.cmj.oa.entity.ClaimVoucher;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("claimVoucherDao")
public interface ClaimVoucherDao {
    void insert(ClaimVoucher claimVoucher);
    void update(ClaimVoucher claimVoucher);
    void delete(int id);
    ClaimVoucher select(int id);
    List<ClaimVoucher> selectByCreateSn(String csn);

    List<ClaimVoucher> selectForCAHIER(String csn);//为审核员获取自己处理过和报销人员已提交的报销单
    List<ClaimVoucher> selectForAUDITLEADER(String csn);//为审核负责人获取已通过，未通过和已审核的报销单
   // List<ClaimVoucher> selectForAUDITLEADER(String csn);

    List<ClaimVoucher> selectByNextDealSn(String ndsn);
    List<ClaimVoucher> fuzzyQuery(@Param("createSn")String createSn,@Param("status")String status, @Param("totalAmount")Double totalAmount, @Param("create_time")String create_time,@Param("cause")String cause);
    List<ClaimVoucher> fuzzyQueryForDeal(@Param("createSn")String createSn,@Param("nextDealsn")String nextDealsn,@Param("status")String status, @Param("totalAmount")Double totalAmount, @Param("create_time")String create_time,@Param("cause")String cause);
}
