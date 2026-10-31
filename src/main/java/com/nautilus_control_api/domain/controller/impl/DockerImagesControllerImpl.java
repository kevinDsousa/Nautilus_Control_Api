package com.nautilus_control_api.domain.controller.impl;

import com.github.dockerjava.api.model.Image;
import com.nautilus_control_api.domain.controller.DockerImagesController;
import com.nautilus_control_api.domain.service.DockerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/docker/hosts/{hostId}/images")
public class DockerImagesControllerImpl implements DockerImagesController {
    private final DockerService dockerService;

    @Override
    @GetMapping("/list")
    public List<Image> listImages(@PathVariable UUID hostId) {
        return dockerService.listImages(hostId);
    }

    @Override
    @GetMapping("/filter")
    public List<Image> filterImages(@PathVariable UUID hostId, @RequestParam(required = false, defaultValue = "image-") String imageName) {
        return dockerService.filterImages(hostId, imageName);
    }
}
