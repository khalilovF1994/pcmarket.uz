package com.example.khalilovf.service;

import com.example.khalilovf.entity.Attachment;
import com.example.khalilovf.entity.AttachmentContent;
import com.example.khalilovf.repository.AttachmentContentRepository;
import com.example.khalilovf.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final AttachmentContentRepository attachmentContentRepository;


    public String upload(MultipartHttpServletRequest multipartHttpServletRequest) throws IOException {
        Iterator<String> fileNames = multipartHttpServletRequest.getFileNames();
        MultipartFile file = multipartHttpServletRequest.getFile(fileNames.next());
        Attachment attachment = new Attachment();

        if (file != null) {
            attachment.setFileOriginalName(file.getOriginalFilename());
            attachment.setContentType(file.getContentType());
            attachment.setSize(file.getSize());
            Attachment save = attachmentRepository.save(attachment);
            AttachmentContent attachmentContent = new AttachmentContent();
            attachmentContent.setAttachment(save);
            attachmentContent.setBytes(file.getBytes());
            attachmentContentRepository.save(attachmentContent);
            return "file success upload attachment id=" + attachmentContent.getAttachment().getId();
        }
        return "file not found";
    }

    public void download(HttpServletResponse httpServletResponse,@PathVariable Integer id) throws IOException {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isPresent()){
            Attachment attachment = optionalAttachment.get();
            Optional<AttachmentContent> content = attachmentContentRepository.findByAttachmentId(id);
            if (content.isPresent()){
                AttachmentContent attachmentContent = content.get();
                httpServletResponse.setHeader("Content-Disposition",
                        "attachment; filename=\""+attachment.getFileOriginalName()+"\"");
                httpServletResponse.setContentType(attachment.getContentType());
                FileCopyUtils.copy(attachmentContent.getBytes(),httpServletResponse.getOutputStream());
            }
        }
    }
}
