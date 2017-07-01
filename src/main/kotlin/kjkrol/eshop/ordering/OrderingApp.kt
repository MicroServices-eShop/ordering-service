package kjkrol.eshop.ordering

import kjkrol.eshop.ordering.order.OrderController
import org.slf4j.LoggerFactory.getLogger
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication.run
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.netflix.feign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.hateoas.config.EnableHypermediaSupport
import springfox.documentation.swagger2.annotations.EnableSwagger2


fun main(args: Array<String>) {
    run(OrderingApp::class.java, *args)
}

@EnableHypermediaSupport(type = arrayOf(EnableHypermediaSupport.HypermediaType.HAL))
@EnableSwagger2
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
open class OrderingApp {

    companion object {
        val log: org.slf4j.Logger = getLogger(OrderController::class.java)
    }

    @Bean
    internal fun init(): CommandLineRunner {
        log.info("initialize app")
        return CommandLineRunner {
            //            placeOrder.placeOrder(PlaceOrderRequest(customerId = karolId, orderItems = listOf(
//                    OrderItemRequest(productId = laptopId, quantity = 2),
//                    OrderItemRequest(productId = eBookId, quantity = 1))))
//            placeOrder.placeOrder(PlaceOrderRequest(customerId = karolId, orderItems = listOf(
//                    OrderItemRequest(productId = eInkId, quantity = 1))))
//            placeOrder.placeOrder(PlaceOrderRequest(customerId = karolId, orderItems = listOf(
//                    OrderItemRequest(productId = bookId, quantity = 1),
//                    OrderItemRequest(productId = screenId, quantity = 1))))
        }
    }

}


