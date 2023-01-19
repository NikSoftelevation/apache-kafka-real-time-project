package net.javaguides;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
public class WikiMediaChangesProducer {

    private KafkaTemplate<String,String> kafkaTemplate;
    private static final Logger LOGGER= LoggerFactory.getLogger(WikiMediaChangesProducer.class);

    public WikiMediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage() throws InterruptedException {

        String topic="wikimedia_recentChange";
        //to read real time stream data from wikimedia we will use event source

        EventHandler eventHandler=new WikiMediaChangesHandler(kafkaTemplate,topic);
        String url="https://stream.wikimedia.org/v2/stream/recentchange";

        EventSource.Builder builder=new EventSource.Builder(eventHandler, URI.create(url));
        EventSource eventSource=builder.build();

        eventSource.start();

        TimeUnit.MINUTES.sleep(10);
    }
}