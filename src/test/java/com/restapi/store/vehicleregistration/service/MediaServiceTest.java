package com.restapi.store.vehicleregistration.service;

import com.restapi.store.vehicleregistration.domain.VehicleMedia;
import com.restapi.store.vehicleregistration.exception.ResourceNotFoundException;
import com.restapi.store.vehicleregistration.repository.VehicleMediaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MediaServiceTest {

    @Mock
    private VehicleMediaRepository mediaRepository;

    @InjectMocks
    private MediaService service;

    @Test
    void uploadMedia_Success() throws IOException {
        MultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test data".getBytes());
        VehicleMedia media = VehicleMedia.builder().id(1L).fileName("test.jpg").fileType("image/jpeg").build();
        
        when(mediaRepository.save(any(VehicleMedia.class))).thenReturn(media);

        Long id = service.uploadMedia(file);

        assertEquals(1L, id);
        verify(mediaRepository, times(1)).save(any(VehicleMedia.class));
    }

    @Test
    void getMedia_Success() {
        VehicleMedia media = VehicleMedia.builder().id(1L).fileName("test.jpg").build();
        when(mediaRepository.findById(1L)).thenReturn(Optional.of(media));

        VehicleMedia result = service.getMedia(1L);

        assertNotNull(result);
        assertEquals("test.jpg", result.getFileName());
    }

    @Test
    void getMedia_NotFound() {
        when(mediaRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getMedia(1L));
    }
}