package club.tempvs.profile.service

import club.tempvs.profile.amqp.ImageEventProcessor
import club.tempvs.profile.dto.ImageDto
import club.tempvs.profile.service.impl.ImageServiceImpl
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import spock.lang.Specification
import spock.lang.Subject

class ImageServiceSpec extends Specification {

    ImageEventProcessor imageEventProcessor = Mock ImageEventProcessor

    @Subject
    ImageService imageService = new ImageServiceImpl(imageEventProcessor)

    ImageDto imageDto = Mock ImageDto
    MessageChannel messageChannel = Mock MessageChannel

    def "replace avatar"() {
        when:
        imageService.store(imageDto)

        then:
        1 * imageEventProcessor.replace() >> messageChannel
        1 * messageChannel.send(_ as Message)
        0 * _
    }

    def "delete avatar"() {
        given:
        String belongsTo = 'profile'
        Long entityId = 1L

        when:
        imageService.delete(belongsTo, entityId)

        then:
        1 * imageEventProcessor.deleteForEntity() >> messageChannel
        1 * messageChannel.send(_ as Message)
    }
}
