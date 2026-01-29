package com.fileinnout.domain.Editor;

public class EditorService {
    private final EditorRepository editorRepository;

    public EditorService(EditorRepository editorRepository) {
        this.editorRepository = editorRepository;
    }

    public EditorDto.NewDocRes CreateRes(EditorDto.NewDocReq req) {

        return editorRepository.create(req);
    }
    public EditorDto.ViewDocRes ViewRes(EditorDto.ViewDocReq req) {

        return editorRepository.view(req);
    }
    public EditorDto.EditDocRes EditRes(EditorDto.EditDocReq req) {

        return editorRepository.edit(req);
    }
    public EditorDto.DeleteDocRes DeleteRes(EditorDto.DeleteDocReq req) {

        return editorRepository.delete(req);
    }
    public EditorDto.SaveDocRes SaveRes(EditorDto.SaveDocReq req) {

        return editorRepository.save(req);
    }
    public EditorDto.PermissionDocRes PermissionRes(EditorDto.PermissionDocReq req) {

        return editorRepository.permission(req);
    }
}
