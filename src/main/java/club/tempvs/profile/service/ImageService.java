package club.tempvs.profile.service;

import club.tempvs.profile.dto.ImageDto;

public interface ImageService {

    void delete(String belongsTo, Long entityId);

    void store(ImageDto imageDto);
}
