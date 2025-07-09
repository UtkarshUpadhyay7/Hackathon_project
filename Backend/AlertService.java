package com.expirytracker.service;

import com.expirytracker.model.Product;
import com.expirytracker.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AlertService {

    @Autowired
    private ProductRepository productRepository;

    // ✅ 1. Used when saving a single product (manual or QR)
    public String checkSingleProductAlert(Product product) {
        LocalDate today = LocalDate.now();
        LocalDate expiryDate = product.getExpiryDate();
        int alertDays = product.getAlertDays();

        if (expiryDate == null) {
            return null;
        }

        // 🔔 Already expired
        if (today.isAfter(expiryDate)) {
            return "⚠️ ALERT: Product '" + product.getName() + "' is already expired!";
        }

        // 🔔 Approaching expiry
        LocalDate alertStartDate = expiryDate.minusDays(alertDays);
        if (!today.isBefore(alertStartDate)) {
            return "⏰ WARNING: Product '" + product.getName() + "' is expiring soon on " + expiryDate + "!";
        }

        // ✅ No alert
        return null;
    }

    // ✅ 2. Used to check all products (for background job or admin panel)
    public void checkForExpiryAlerts() {
        List<Product> allProducts = productRepository.findAll();
        LocalDate today = LocalDate.now();

        for (Product product : allProducts) {
            String message = checkSingleProductAlert(product);
            if (message != null) {
                System.out.println(message); // You can also log this or send SMS/email if needed
            }
        }
    }
}
