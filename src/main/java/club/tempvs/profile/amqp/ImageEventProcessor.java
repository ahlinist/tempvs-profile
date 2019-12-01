package club.tempvs.profile.amqp;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ImageEventProcessor {

    String REPLACE_IMAGE = "image.replace";
    String DELETE_IMAGE = "image.delete-for-entity";

    @Output(DELETE_IMAGE)
    MessageChannel deleteForEntity();

    @Output(REPLACE_IMAGE)
    MessageChannel replace();
}
