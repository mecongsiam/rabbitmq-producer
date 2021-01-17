package com.course.rabbitmqproducer.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.stream.Collectors;

//@Service
public class RabbitmqScheduler {

	private static final Logger log = LoggerFactory.getLogger(RabbitmqScheduler.class);

	@Autowired
	private RabbitmqProxyService rabbitmqProxyService;

	@Scheduled(fixedDelay = 90000)
	public void sweepDirtyQueues() {
		try {
			var dirtyQueues = rabbitmqProxyService.getAllQueues().stream().filter(p -> p.isDirty())
					.collect(Collectors.toList());
			dirtyQueues.forEach(q -> log.info("Queue {} has {} unprocessed messages", q.getName(), q.getMessages()));
		} catch (Exception e) {
			log.error("Cannot sweep queues : {}", e.getMessage());
		}
	}

}
