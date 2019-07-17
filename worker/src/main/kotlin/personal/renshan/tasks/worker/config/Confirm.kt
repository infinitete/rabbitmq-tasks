package personal.renshan.tasks.worker.config

import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.connection.CorrelationData
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class Confirm: RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    @Autowired
    lateinit var rabbitTemplate: RabbitTemplate

    @PostConstruct
    fun postConstruct() {
        rabbitTemplate.setMandatory(true)
        rabbitTemplate.setConfirmCallback(this)
        rabbitTemplate.setReturnCallback(this)
    }

    override fun confirm(p0: CorrelationData?, p1: Boolean, p2: String?) {
        println("确认消息： $p0, $p1, $p2")
    }

    override fun returnedMessage(p0: Message, p1: Int, p2: String, p3: String, p4: String) {
        println("返回消息： $p0, $p1, $p2, $p3, $p4")
    }
}