package br.com.claudsan.store.application.service;

import br.com.claudsan.store.adapter.dto.UserViewDTO;
import br.com.claudsan.store.adapter.metrics.CustomMetrics;
import br.com.claudsan.store.application.domain.Order;
import br.com.claudsan.store.application.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender emailSender;

    private static final String METRIC_NAME = "store.email";
    @Autowired
    private CustomMetrics metrics;

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Autowired
    private UserService userService;

    public void sendOrderNotification(Order order) {
        try {
            UserViewDTO user = userService.getUser(order.getUser().getUserId());
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailFrom);
            message.setTo(user.getEmail());
            message.setSubject(String.format("Order ID: %d is Finished", order.getOrderId()));
            message.setText("The order has been fully completed.\n\n Thanks Store-API");
            emailSender.send(message);
            logger.info(String.format("Email sent! Order Id: %d, User ID:%d", order.getOrderId(), order.getUser().getUserId()));
            metrics.counter(METRIC_NAME, "type", "success", "order", order.getOrderId().toString(), "sendto", order.getUser().getUserId().toString());
        } catch (Exception e) {
            logger.error(String.format("Error on sending email! Order Id: %d, User ID:%d", order.getOrderId(), order.getUser().getUserId()));
            metrics.counter(METRIC_NAME, "type", "error", "order", order.getOrderId().toString(), "sendto", order.getUser().getUserId().toString());
        }
    }
}
