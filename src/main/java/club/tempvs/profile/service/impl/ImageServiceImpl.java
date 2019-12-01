package club.tempvs.profile.service.impl;

import club.tempvs.profile.amqp.ImageEventProcessor;
import club.tempvs.profile.dto.ImageDto;
import club.tempvs.profile.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static org.springframework.messaging.support.MessageBuilder.withPayload;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageEventProcessor imageEventProcessor;

    @Override
    public void store(ImageDto payload) {
        imageEventProcessor.replace()
                .send(withPayload(payload).build());
    }

    @Override
    public void delete(String belongsTo, Long entityId) {
        String query = String.format("%1$s::%2$d", belongsTo, entityId);
        imageEventProcessor.deleteForEntity()
                .send(withPayload(query).build());
    }
}
