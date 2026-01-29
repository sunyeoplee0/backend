package com.fileinnout.domain.Editor;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class EditorDto {
/*    private BigInteger id_idx;
    private BigInteger post_idx;
    private String title;
    private String contents;
    private String Type;
    private String updatedAt;
    private String group;
    private Map<Integer, Integer> permission; */

    //문서 생성 요청 idx랑 문서 확장자, 개인으로 할지 공유할지 결정할 group
    // group == shared, public 일 경우 받을 permission
    public record NewDocReq (
            Long id_idx,
            String doc_type,
            String group
//            List<Permission> permission
    ){}
    //문서 생성 응답 idx랑 문서 확장자, 개인으로 할지 공유할지 결정할 group
    // group == shared, public 일 경우 받을 permission
    public record NewDocRes (
            Long id_idx,
            Long post_idx,
            String group
//            List<Permission> permission
    ){}

    // 문서 조회 요청 idx랑 포스트 idx 필요.
    public record ViewDocReq (
            Long id_idx,
            Long post_idx
    ){}
    // 문서 조회 응답 idx랑 포스트 idx 필요, 문서 조회시 조회 가능한 사람인지 확인
    public record ViewDocRes (
            Long id_idx,
            Long post_idx,
            String title,
            String contents,
            String updatedAt
//            List<Permission> permission
    ){}

    // 문서 편집 요청 idx랑 post_idx 필요, 문서 편집시 permission의 키가 idx이며 권한을 조회함.
    public record EditDocReq (
            Long id_idx,
            Long post_idx
//            List<Permission> permission
    ){}
    // 문서 편집 응답 idx랑 post_idx 필요, 문서 편집시 title, contents
    // permission의 권한 확인 후 편집 가능
    public record EditDocRes (
            Long id_idx,
            Long post_idx,
            String title,
            String contents
//            List<Permission> permission
    ){}

    // 문서 삭제 요청 idx랑 post_idx 필요, 문서 삭제시 permission의 키가 idx이며 권한을 조회함.
    public record DeleteDocReq (
            Long id_idx,
            Long post_idx
//            List<Permission> permission
    ){}
    // 문서 삭제 응답 idx랑 post_idx 필요, 문서 삭제시 permission의 키가 idx이며 권한을 조회함.
    public record DeleteDocRes (
            Long id_idx,
            Long post_idx
//            List<Permission> permission
    ){}

    // 문서 저장 요청 idx랑 post_idx 필요, 문서 저장시 필요한 title, contents, type 필요
    public record SaveDocReq (
            Long id_idx,
            Long post_idx,
            String title,
            String contents,
            String doc_type
    ){}
    // 문서 저장 요청 idx랑 post_idx 필요, 문서 저장시 필요한 title, contents, type 필요
    public record SaveDocRes (
            Long id_idx,
            Long post_idx
    ){}
}
//class Permission {
//    Long idx;
//    Integer permission;
//    Permission(Long idx, Integer permission) {
//        this.idx = idx;
//        this.permission = permission;
//    }
//}