package com.cmj.oa.entity;

import lombok.Data;


@Data
public class ClaimVoucherItem {

	private Integer id;

	private Integer claimVoucherId;

	private String item;

	private Double amount;

	private String comment;

    private String type;

	private String hospital;

	private String keshi;

	private String guahaoType;

	private String document;

	private Integer realId;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Integer getRealId() {
        return realId;
    }

    public void setRealId(Integer realId) {
        this.realId = realId;
    }

}
