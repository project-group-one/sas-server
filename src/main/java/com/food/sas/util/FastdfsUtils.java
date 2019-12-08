package com.food.sas.util;

import com.food.sas.data.entity.FileInfo;
import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

@Component
public class FastdfsUtils {
    public static final String DEFAULT_CHARSET = "UTF-8";

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    /**
     * 上传
     *
     * @param file
     * @return
     * @throws IOException
     */
    public StorePath upload(MultipartFile file) throws IOException {
        // 设置文件信息
        Set<MetaData> mataData = new HashSet<>();
        mataData.add(new MetaData("author", "fastdfs"));
        mataData.add(new MetaData("description", file.getOriginalFilename()));
        // 上传
        return fastFileStorageClient.uploadFile(
                file.getInputStream(), file.getSize(),
                FilenameUtils.getExtension(file.getOriginalFilename()),
                null);
    }

    /**
     * 删除
     *
     * @param path
     */
    public void delete(String path) {
        fastFileStorageClient.deleteFile(path);
    }

    /**
     * 删除
     *
     * @param group
     * @param path
     */
    public void delete(String group, String path) {
        fastFileStorageClient.deleteFile(group, path);
    }

}