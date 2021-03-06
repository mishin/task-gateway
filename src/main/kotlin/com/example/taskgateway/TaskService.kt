package com.example.taskgateway.kafka

import com.example.taskgateway.TaskContract
import com.example.taskgateway.kafka.producer.KafkaTaskProducer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TaskService(
        @Autowired
        private val taskContracts: List<TaskContract>,
        @Autowired
        private val taskProducer: KafkaTaskProducer) {


    fun process(event: String) {
        taskContracts
                .filter { it.validate(event) }
                .map { it.generateTasks(event) }.flatten()
                .map { it -> taskProducer.produce(it) }
    }
}