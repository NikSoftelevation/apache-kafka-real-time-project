package net.javaguides;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.MessageEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public class WikiMediaChangesHandler implements EventHandler {


    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    private static final Logger LOGGER= LoggerFactory.getLogger(WikiMediaChangesHandler.class);

    private String topic;

    @Override
    public void onOpen() throws Exception {
    }
    @Override
    public void onClosed() throws Exception {
    }
    @Override
    public void onMessage(String s, MessageEvent messageEvent) throws Exception {

        LOGGER.info(String.format("Event data -> %s",messageEvent.getData()));

        kafkaTemplate.send(topic,messageEvent.getData());
    }
    @Override
    public void onComment(String s) throws Exception {
    }
    @Override
    public void onError(Throwable throwable) {
    }
}
