package com.logon.pet.petlogoncore.enums;

/**
 * 账户状态
 */
public enum AccountStatus {

    NORMAL("正常", "0"),
    FROZEN("冻结", "0"),
    DISABLED("正常", "0");

    private String name;
    private String value;

    AccountStatus(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
