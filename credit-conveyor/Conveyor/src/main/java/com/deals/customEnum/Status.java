package com.deals.customEnum;

public enum Status {

    CC_DENIED("Denied_by_credit_conveyor"),
    CC_APPROVED("Approved_by_credit_conveyor"),
    PREAPPROVAL,
    APPROVED,
    PREPARE_DOCUMENTS,
    DOCUMENT_CREATED,
    CLIENT_DENIED,
    DOCUMENT_SIGNED,
    CREDIT_ISSUED;
    private String statuses;
    private String code;
    Status(String code){
        this.code = code;
    }

    Status() {

    }

    public String getStatus() {
        return statuses;
    }

    public String getCode() {
        return code;
    }
}
