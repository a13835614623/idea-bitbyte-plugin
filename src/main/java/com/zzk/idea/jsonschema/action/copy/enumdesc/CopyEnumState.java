package com.zzk.idea.jsonschema.action.copy.enumdesc;

import com.zzk.idea.jsonschema.constants.EnumParamType;

import java.util.List;

public class CopyEnumState {
    /**
     * 参数列表
     */
    private List<EnumParamType> paramTypes;
    /**
     * 多个枚举描述的分隔符
     */
    private String descSplit;
    /**
     * 参数的分隔符
     */
    private String paramSplit;

    public List<EnumParamType> getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(List<EnumParamType> paramTypes) {
        this.paramTypes = paramTypes;
    }

    public String getDescSplit() {
        return descSplit;
    }

    public void setDescSplit(String descSplit) {
        this.descSplit = descSplit;
    }

    public String getParamSplit() {
        return paramSplit;
    }

    public void setParamSplit(String paramSplit) {
        this.paramSplit = paramSplit;
    }

    public static CopyEnumState defaultVal() {
        CopyEnumState copyEnumState = new CopyEnumState();
        copyEnumState.setDescSplit(",");
        copyEnumState.setParamSplit("-");
        copyEnumState.setParamTypes(List.of(EnumParamType.NAME, EnumParamType.COMMENT));
        return copyEnumState;
    }
}
