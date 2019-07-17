package personal.renshan.tasks.provider.config

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.core.AmqpAdmin



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
    fun amqpAdmin(cf: ConnectionFactory): AmqpAdmin {
        return RabbitAdmin(cf)
    }

    @Bean
    fun bindExchangeWithQueue(queue: Queue, topicExchange: TopicExchange): Binding {
        return BindingBuilder.bind(queue).to(topicExchange).with("times.worker")
    }


    @Bean
    fun template(cf: ConnectionFactory): RabbitTemplate {
        return RabbitTemplate(cf)
    }

}