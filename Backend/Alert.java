package com.expirytracker.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity // Tells Spring Boot this class is a database table
@Table(name = "alerts") // The table name in your MySQL database
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long id;

    private String alertType; // Like: "Expired" or "Expiring Soon"

    @Column(columnDefinition = "TEXT")
    private String alertMessage; // The actual alert message like "Milk expired!"

    private LocalDateTime createdAt; // Time when alert was created

    @ManyToOne
    @JoinColumn(name = "product_id") // Links this alert to a specific product
    private Product product;

    // Automatically set the current date/time when saving alert
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters & Setters (used to access/update values)

    public Long getId() {
        return id;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
