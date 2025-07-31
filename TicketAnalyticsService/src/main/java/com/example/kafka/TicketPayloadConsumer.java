package com.example.kafka;

import com.example.domain.TicketPayload;
import com.example.repository.TicketPayloadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketPayloadConsumer {

    private final TicketPayloadRepository ticketPayloadRepository;

    @KafkaListener(topics = "ticket-purchases", groupId = "ticket-purchase-group")
    public void consume(TicketPayload data) {
        log.debug(data.toString());
        ticketPayloadRepository.saveTicketPayload(data);
    }
}
