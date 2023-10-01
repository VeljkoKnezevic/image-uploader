package com.veljkoknezevic.imageuploader.repository;

import com.veljkoknezevic.imageuploader.entity.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image, Long> {
    Image findByName(String name);
}
