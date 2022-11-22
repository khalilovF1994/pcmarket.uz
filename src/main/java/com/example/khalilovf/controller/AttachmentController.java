package com.example.khalilovf.controller;

import com.example.khalilovf.repository.AttachmentContentRepository;
import com.example.khalilovf.repository.AttachmentRepository;
import com.example.khalilovf.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/attachment")
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping("/upload")
    public String upload(MultipartHttpServletRequest multipartHttpServletRequest) throws IOException {
        return attachmentService.upload(multipartHttpServletRequest);

    }

    @GetMapping("/download/{id}")
    public void download(HttpServletResponse httpServletResponse, @PathVariable Integer id) throws IOException {
        attachmentService.download(httpServletResponse, id);
    }
}
