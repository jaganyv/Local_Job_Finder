package com.jobmarketplace.service;

import com.jobmarketplace.model.Job;
import com.jobmarketplace.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationService {

    @Value("${whatsapp.api.url}")
    private String whatsappApiUrl;

    @Value("${whatsapp.api.instance-id}")
    private String instanceId;

    @Value("${whatsapp.api.token}")
    private String token;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendJobAcceptanceNotification(User seeker, Job job) {
        if (seeker.getPhone() == null || seeker.getPhone().isEmpty()) {
            return; // Skip if no phone number
        }

        String message = String.format(
            "🎉 Congratulations! Your application for '%s' has been accepted!\n\n" +
            "Job Title: %s\n" +
            "Salary: %s\n" +
            "Type: %s\n\n" +
            "Please contact the employer for next steps.",
            job.getTitle(),
            job.getTitle(),
            job.getSalary() != null ? "$" + job.getSalary() : "Not specified",
            job.getJobType() != null ? job.getJobType().name() : "Not specified"
        );

        try {
            sendWhatsAppMessage(seeker.getPhone(), message);
        } catch (Exception e) {
            System.err.println("Failed to send WhatsApp notification: " + e.getMessage());
            // In production, you might want to log this or use a fallback
        }
    }

    private void sendWhatsAppMessage(String phoneNumber, String message) {
        // Format phone number (remove any non-digit characters except +)
        String formattedPhone = phoneNumber.replaceAll("[^0-9+]", "");
        if (!formattedPhone.startsWith("+")) {
            formattedPhone = "+" + formattedPhone;
        }

        String url = whatsappApiUrl + "/" + instanceId + "/messages/chat";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        Map<String, String> body = new HashMap<>();
        body.put("to", formattedPhone);
        body.put("body", message);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("WhatsApp API returned error: " + response.getBody());
        }
    }
}

