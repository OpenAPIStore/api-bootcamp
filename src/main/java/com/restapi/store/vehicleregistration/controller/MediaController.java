package com.restapi.store.vehicleregistration.controller;

import com.restapi.store.vehicleregistration.domain.VehicleMedia;
import com.restapi.store.vehicleregistration.service.MediaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/media")
@RequiredArgsConstructor
@Slf4j
public class MediaController {

    private final MediaService mediaService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<Map<String, String>> uploadMedia(@RequestParam("file") MultipartFile file) {
        log.info("Received multipart request to upload media");
        return CompletableFuture.supplyAsync(() -> {
            Long mediaId = mediaService.uploadMedia(file);
            return Map.of(
                    "message", "File uploaded successfully",
                    "mediaId", String.valueOf(mediaId),
                    "url", "/api/v1/media/" + mediaId
            );
        });
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<Resource>> downloadMedia(@PathVariable Long id) {
        log.info("Received binary request to download media id: {}", id);
        return CompletableFuture.supplyAsync(() -> {
            VehicleMedia media = mediaService.getMedia(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(media.getFileType() != null ? media.getFileType() : "application/octet-stream"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + media.getFileName() + "\"")
                    .body(new ByteArrayResource(media.getData()));
        });
    }
}
