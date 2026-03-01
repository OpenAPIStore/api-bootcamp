package com.restapi.store.vehicleregistration.service;

import com.restapi.store.vehicleregistration.domain.VehicleMedia;
import com.restapi.store.vehicleregistration.exception.ResourceNotFoundException;
import com.restapi.store.vehicleregistration.repository.VehicleMediaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaService {

    private final VehicleMediaRepository mediaRepository;

    @Transactional
    public Long uploadMedia(MultipartFile file) {
        log.info("Uploading media file: {}", file.getOriginalFilename());
        try {
            VehicleMedia media = VehicleMedia.builder()
                    .fileName(file.getOriginalFilename())
                    .fileType(file.getContentType())
                    .data(file.getBytes())
                    .build();
            VehicleMedia savedMedia = mediaRepository.save(media);
            return savedMedia.getId();
        } catch (IOException e) {
            log.error("Failed to upload media", e);
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public VehicleMedia getMedia(Long id) {
        log.info("Fetching media with id: {}", id);
        return mediaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Media not found with id: " + id));
    }
}