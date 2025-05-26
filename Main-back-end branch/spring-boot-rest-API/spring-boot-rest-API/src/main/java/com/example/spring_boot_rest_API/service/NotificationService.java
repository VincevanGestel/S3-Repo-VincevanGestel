package com.example.spring_boot_rest_API.service;

import com.example.spring_boot_rest_API.dto.ProductDTO;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class NotificationService {

    private final RestTemplate restTemplate;
    private final Queue<ProductDTO> retryQueue = new ConcurrentLinkedQueue<>();
    private final NotificationService self; // proxy to enable async self-calls

    public NotificationService(RestTemplate restTemplate, ApplicationContext context) {
        this.restTemplate = restTemplate;
        this.self = context.getBean(NotificationService.class); // get proxy for async
    }

    @Async
    public void notifyProduct(ProductDTO product) {
        try {
            restTemplate.postForEntity("http://localhost:8081/api/notify", product, Void.class);
            System.out.println("✅ Notification sent for product: " + product.getName());
        } catch (Exception e) {
            System.err.println("❌ Failed to notify second backend. Queuing retry: " + e.getMessage());
            retryQueue.add(product);
        }
    }

    @Scheduled(fixedRate = 5 * 60 * 1000) // every 5 minutes
    public void retryFailedNotifications() {
        int retryCount = retryQueue.size();
        for (int i = 0; i < retryCount; i++) {
            ProductDTO product = retryQueue.poll();
            if (product != null) {
                self.notifyProduct(product); // async call via proxy
            }
        }
    }
}
