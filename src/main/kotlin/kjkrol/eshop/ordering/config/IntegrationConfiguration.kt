package kjkrol.eshop.ordering.config

import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.cloud.client.hypermedia.DynamicServiceInstanceProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class IntegrationConfiguration {

    @Bean("productCatalogServiceInstanceProvider")
    internal fun productCatalogServiceInstanceProvider(discoveryClient: DiscoveryClient): DynamicServiceInstanceProvider =
            DynamicServiceInstanceProvider(discoveryClient, "PRODUCT-CATALOG-SERVICE")

    @Bean("customerRegisterServiceInstanceProvider")
    internal fun customerRegisterServiceInstanceProvider(discoveryClient: DiscoveryClient): DynamicServiceInstanceProvider =
            DynamicServiceInstanceProvider(discoveryClient, "CUSTOMER-REGISTER-SERVICE")

}