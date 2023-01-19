package net.javaguides;

import lombok.RequiredArgsConstructor;
import net.javaguides.entity.WikiMediaData;
import net.javaguides.repository.WikiMediaDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaDatabaseConsumer{
    private WikiMediaDataRepository wikiMediaDataRepository;

    private static final Logger LOGGER= LoggerFactory.getLogger(KafkaDatabaseConsumer.class);

    public KafkaDatabaseConsumer(WikiMediaDataRepository wikiMediaDataRepository) {
        this.wikiMediaDataRepository = wikiMediaDataRepository;
    }

    @KafkaListener(topics="wikimedia_recentChange",groupId="myGroup")
    public void consume(String eventMessage){

        LOGGER.info(String.format("Event message received -> %s",eventMessage));

        WikiMediaData wikiMediaData= new WikiMediaData();

        wikiMediaData.setWikiEventData(eventMessage);

        wikiMediaDataRepository.save(wikiMediaData);
    }
}