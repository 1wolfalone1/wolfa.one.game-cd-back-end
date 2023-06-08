package com.wolfalone.gamecdbackend.service;

import com.wolfalone.gamecdbackend.dto.ImageDTO;
import com.wolfalone.gamecdbackend.entity.Image;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ImageService {

    List<ImageDTO> mapListImageToListDTO(List<Image> images);
}
