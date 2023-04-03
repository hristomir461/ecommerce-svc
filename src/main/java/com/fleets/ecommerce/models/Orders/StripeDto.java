package com.fleets.ecommerce.models.Orders;

public class StripeDto {
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public StripeDto(String sessionId) {
        this.sessionId = sessionId;
    }

    public StripeDto() {
    }
}
