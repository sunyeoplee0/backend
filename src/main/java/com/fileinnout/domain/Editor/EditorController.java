package com.fileinnout.domain.Editor;

import com.fileinnout.global.BaseResponse;
import com.fileinnout.global.Controller;
import com.fileinnout.utils.JsonParser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fileinnout.domain.Editor.EditorDto.*;

public class EditorController implements Controller {
    private final EditorService editorService;

    public EditorController(EditorService editorService) {
        this.editorService = editorService;
    }

    @Override
    public BaseResponse process(HttpServletRequest req, HttpServletResponse resp) {

        if(req.getRequestURI().contains("create")) {
            NewDocReq createReq = JsonParser.from(req, NewDocReq.class);
            return BaseResponse.success(editorService.CreateRes(createReq));

        } else if (req.getRequestURI().contains("view")) {
            ViewDocReq viewReq = JsonParser.from(req, ViewDocReq.class);
            return BaseResponse.success(editorService.ViewRes(viewReq));

        } else if (req.getRequestURI().contains("edit")) {
            EditDocReq editReq = JsonParser.from(req, EditDocReq.class);
            return BaseResponse.success(editorService.EditRes(editReq));

        } else if (req.getRequestURI().contains("delete")) {
            DeleteDocReq deleteReq = JsonParser.from(req, DeleteDocReq.class);
            return BaseResponse.success(editorService.DeleteRes(deleteReq));

        } else if (req.getRequestURI().contains("save")) {
            SaveDocReq saveReq = JsonParser.from(req, SaveDocReq.class);
            return BaseResponse.success(editorService.SaveRes(saveReq));

        } else if (req.getRequestURI().contains("permission")) {
            PermissionDocReq permissionReq = JsonParser.from(req, PermissionDocReq.class);
            return BaseResponse.success(editorService.PermissionRes(permissionReq));

        }
        return null;
    }
}
