package com.nautilus_control_api.domain.controller;

import com.github.dockerjava.api.model.Image;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

public interface DockerImagesController {
    List<Image> listImages(@PathVariable UUID hostId);
    List<Image> filterImages(@PathVariable UUID hostId, String imageName);
}
