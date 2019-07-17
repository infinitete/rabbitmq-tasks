package personal.renshan.tasks.provider

import org.springframework.stereotype.Component
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled


@Component
@Configuration
@EnableScheduling
class Processor {
    @Autowired
    private lateinit var rabbitTemplate: AmqpTemplate

    @Scheduled(initialDelay = 100, fixedRate = 3000000)
    fun doSend() {
        var i = 1
        while (i ++ < 1000) {
            Thread.sleep(100)
            rabbitTemplate.convertAndSend("times.worker", i)
        }
    }
}