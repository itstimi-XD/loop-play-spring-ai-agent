package com.baedal.support;

import java.util.List;

public record SupportResponse(
        String summary,
        Category category,
        Urgency urgency,
        String nextAction,
        List<String> neededInfo,
        List<ResponsibleParty> responsibleParties,
        List<String> suspicionSignals
) {
    public enum Category { ORDER, DELIVERY, REFUND, PAYMENT, COMPLAINT, ETC }
    public enum Urgency  { LOW, NORMAL, HIGH, CRITICAL }
    public enum ResponsibleParty { RIDER, STORE, PLATFORM, UNCLEAR }
}
