package com.logon.pet.petlogoncore.enums;

/**
 * 删除标志
 */
public enum CommonDeleteFlag {

    NORMAL("正常", "0"),
    DELETE("已删除", "1");

    private String name;
    private String value;

    CommonDeleteFlag(String name, String value) {
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
