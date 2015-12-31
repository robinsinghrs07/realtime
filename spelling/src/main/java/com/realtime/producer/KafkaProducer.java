package com.realtime.producer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Logger; 
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.realtime.common.LogGenerator;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class KafkaProducer<K, V> {

	public static final Logger LOG = LogGenerator.createLogs(KafkaProducer.class,"KafkaProducer",true); 

	private final Producer producer;
	private final String topic;

	public KafkaProducer(Properties properties, String topic) {
		this.topic = topic;
		producer = new org.apache.kafka.clients.producer.KafkaProducer(
				properties);
	}

	public void sendMessage(V value) {
		ProducerRecord record = new ProducerRecord(topic, value);
		LOG.info("SENDING MESSAGE TO TOPIC:: " + topic + "\nValue:: "
				+ record.value());
		producer.send(record);
	}

	public void sendMessage(K key, V value) {
		ProducerRecord record = new ProducerRecord(topic, key, value);
		LOG.info("Sending message to topic:: " + topic + "\nKey:: "
				+ record.key() + "\nValue:: " + record.value());
		producer.send(record);
	}

	public void sendMessages(Map<K, V> keyValueMap) {
		for (Entry<K, V> entry : keyValueMap.entrySet()) {
			sendMessage(entry.getKey(), entry.getValue());
		}

	}

	public void sendMessages(List<V> values) {
		for (V value : values) {
			sendMessage(value);
		}

	}

	public void close() {
		LOG.info("Closing producer");
		if (producer != null) {
			producer.close();
		}
	}


	public static void main(String args[]) {
		Properties properties = new Properties();
		properties.put("bootstrap.servers", "localhost:9092");
		properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
		properties.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
		KafkaProducer<String, String> genericKafkaProducer = new KafkaProducer(properties, "test"); 
		String msg = "kuch khas nahi";
		Map<String, String> map = new HashMap<String, String>();
		map.put(msg, msg);
		genericKafkaProducer.sendMessages(map);
		genericKafkaProducer.close(); 
	}

}
