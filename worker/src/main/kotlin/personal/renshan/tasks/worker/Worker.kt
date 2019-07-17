package personal.renshan.tasks.worker

import com.rabbitmq.client.Channel
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
@EnableRabbit
class Worker {

    @RabbitListener(queues = ["times.worker"])
    fun worker(now: Long, channel: Channel, message: Message) {
        channel.basicAck(message.messageProperties.deliveryTag, false)
        println("收到: $now")
    }

}