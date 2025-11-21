package com.hse.userflow.storingservice.mapper;

import com.hse.userflow.dto.FileUploadResponseDto;
import com.hse.userflow.storingservice.model.File;

public class FileMapper {

    public static FileUploadResponseDto toDto(File file){
        return FileUploadResponseDto.builder()
                .fileId(file.getId())
                .fileName(file.getOriginalFileName())
                .loadedAt(file.getUploadedAt())
                .build();
    }

}
