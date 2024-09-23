package com.nautilus_control_api.domain.controller;

import com.github.dockerjava.api.model.Image;

import java.util.List;

public interface DockerImagesController {
    List<Image> listImages();
    List<Image> filterImages(String imageName);
}
