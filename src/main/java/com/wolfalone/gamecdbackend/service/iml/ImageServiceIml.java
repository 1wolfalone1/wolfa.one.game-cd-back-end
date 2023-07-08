package com.wolfalone.gamecdbackend.service.iml;

import com.wolfalone.gamecdbackend.dto.ImageDTO;
import com.wolfalone.gamecdbackend.entity.Image;
import com.wolfalone.gamecdbackend.mapper.ImageMapper;
import com.wolfalone.gamecdbackend.service.ImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageServiceIml implements ImageService {

    @Autowired
    private ImageMapper imageMapper;

    @Override
    public List<ImageDTO> mapListImageToListDTO(List<Image> images) {
        return images.stream().map(imageMapper::toDTO).collect(Collectors.toList());
    }

}
