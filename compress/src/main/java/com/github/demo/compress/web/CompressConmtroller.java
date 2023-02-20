package com.github.demo.compress.web;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class CompressConmtroller {


    @GetMapping("/single")
    public void exportSingleFile(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.addHeader("Content-Disposition", "attachment;filename=test.json");

        // 此处导出字符串
        String content = "{\"key\":\"此处导出字符串\"}";
        try (ServletOutputStream outStr = response.getOutputStream();
             BufferedOutputStream buff = new BufferedOutputStream(outStr)) {
            buff.write(content.getBytes(StandardCharsets.UTF_8));
            buff.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/zip")
    public void exportZipFile(HttpServletRequest request, HttpServletResponse response) {
        List<String> texts = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            texts.add("{\"key\":\"此处导出字符串\"}");
        }
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=zip_demo.zip");
        response.setContentType("application/zip");

        try (ServletOutputStream outStr = response.getOutputStream();
             ZipArchiveOutputStream zipOutput = new ZipArchiveOutputStream(outStr)) {
            for (int i = 0; i < texts.size(); i++) {
                byte[] content = texts.get(i).getBytes(StandardCharsets.UTF_8);
                String entryName = "text_" + i + ".json";
                ZipArchiveEntry entry = new ZipArchiveEntry (entryName);
                entry.setSize(content.length);
                zipOutput.putArchiveEntry(entry);
                zipOutput.write(content);
                zipOutput.closeArchiveEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
