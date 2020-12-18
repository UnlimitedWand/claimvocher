package com.cmj.oa.dto;

import com.cmj.oa.entity.ClaimVoucherItem;

import java.util.Arrays;
import java.util.List;

public class ClaimItemsParsed {
    private Integer id;

    private Integer claimVoucherId;

    private String item;

    private Double amount;

    private String comment;

    private String hospital;

    private String keshi;

    private String guahaoType;

    private List<String> documentLists;

    public ClaimItemsParsed(ClaimVoucherItem claimVoucherItem){
        this.id = claimVoucherItem.getId();
        this.claimVoucherId = claimVoucherItem.getClaimVoucherId();
        this.item = claimVoucherItem.getItem();
        this.amount = claimVoucherItem.getAmount();
        this.comment = claimVoucherItem.getComment();
        this.hospital = claimVoucherItem.getHospital();
        this.keshi = claimVoucherItem.getKeshi();
        this.guahaoType = claimVoucherItem.getGuahaoType();
        if(claimVoucherItem.getDocument()!= null) {
            this.documentLists = Arrays.asList(claimVoucherItem.getDocument().split(":"));//将图片名字符串分割为图片名列表
            System.out.println(documentLists);
        }else
            this.documentLists = null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClaimVoucherId() {
        return claimVoucherId;
    }

    public void setClaimVoucherId(Integer claimVoucherId) {
        this.claimVoucherId = claimVoucherId;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getKeshi() {
        return keshi;
    }

    public void setKeshi(String keshi) {
        this.keshi = keshi;
    }

    public String getGuahaoType() {
        return guahaoType;
    }

    public void setGuahaoType(String guahaoType) {
        this.guahaoType = guahaoType;
    }

    public List<String> getDocumentLists() {
        return documentLists;
    }

    public void setDocumentLists(List<String> documentLists) {
        this.documentLists = documentLists;
    }
}
