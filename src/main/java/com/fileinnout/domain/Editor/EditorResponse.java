package com.fileinnout.domain.Editor;

import java.math.BigInteger;
import java.util.Map;

public class EditorResponse {
    private String Method;
    private BigInteger id_idx;
    private BigInteger post_idx;
    private String title;
    private String contents;
    private String Type;
    private String updatedAt;
    private String group;
    private Map<Integer, Integer> permission;

}
