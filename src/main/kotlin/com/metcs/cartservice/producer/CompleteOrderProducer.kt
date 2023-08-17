package com.metcs.cartservice.producer

import com.metcs.cartservice.configuration.COMPLETE_ORDER_TOPIC
import com.metcs.cartservice.domain.events.CompleteOrderEvent
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class CompleteOrderProducer(
    private val kafkaTemplate: KafkaTemplate<String, CompleteOrderEvent>
) {
    fun sendCompleteOrderEvent(completeOrderEvent: CompleteOrderEvent) {
        kafkaTemplate.send(COMPLETE_ORDER_TOPIC, completeOrderEvent)
    }
}
