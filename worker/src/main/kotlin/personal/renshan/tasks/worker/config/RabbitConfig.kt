package personal.renshan.tasks.worker.config

import org.springframework.amqp.core.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory



@Configuration
class RabbitConfig {

    @Bean
    fun workQueue(): Queue {
        return Queue("times.worker", true, false, false)
    }

    @Bean
    fun exchange(): TopicExchange {
        return TopicExchange("times")
    }

    @Bean
    fun bindExchangeWithQueue(queue: Queue, topicExchange: TopicExchange): Binding {
        return BindingBuilder.bind(queue).to(topicExchange).with("times.worker")
    }


    @Bean
    fun jsaFactory(connectionFactory: ConnectionFactory): RabbitListenerContainerFactory<SimpleMessageListenerContainer> {
        val factory = SimpleRabbitListenerContainerFactory()
        factory.setConnectionFactory(connectionFactory)

        //prefetchCount的值设为1 这告诉RabbitMQ不要同时将多个消息分派给一个工作者
        //换句话说，在某个工作者处理完一条消息并确认它之前，RabbitMQ不会给该工作者分派新的消息
        //而是将新的消息分派给下一个不是很繁忙的工作者 这就是fair dispatch

        //如果不设置，默认是round-robin
        factory.setPrefetchCount(1)
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL)

        //AcknowledgeMode.AUTO 队列把消息发送给消费者之后就从队列中删除消息
        //AcknowledgeMode.MANUAL 关闭自动应答，说明队列把消息给消费者之后要等待消费者的确认处理完成消息。
        //如果未收到消费者的确认处理完成消息，就会分发给其他消费者，保证消息不丢失。
        //          factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory
    }
}
