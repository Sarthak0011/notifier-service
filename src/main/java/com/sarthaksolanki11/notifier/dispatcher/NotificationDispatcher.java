package com.sarthaksolanki11.notifier.dispatcher;

import com.sarthaksolanki11.notifier.channels.NotificationSender;
import com.sarthaksolanki11.notifier.enums.NotificationChannels;
import com.sarthaksolanki11.notifier.events.Event;
import com.sarthaksolanki11.notifier.notificationService.NotificationServiceTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationDispatcher {

    private final List<NotificationServiceTemplate> templates;
    private final List<NotificationSender> notificationSenders;

    public void dispatch(Event event) {
        String template = templates.stream()
                .filter(t -> t.supports(event))
                .findFirst()
                .map(t -> t.render(event))
                .orElseThrow(() -> new RuntimeException("Template not found for " + event.getEvent()));

        EnumSet<NotificationChannels> enabledChannels = parseUserChannels(event.getEnabledChannels());
        if (enabledChannels == null) return;

        for (NotificationChannels channel: enabledChannels) {
            notificationSenders.stream()
                    .filter(sender -> sender.supports(channel))
                    .findFirst()
                    .ifPresent(sender -> sender.send(template, event));
        }

    }


    private EnumSet<NotificationChannels> parseUserChannels(List<String> channelList) {


        if (channelList == null || channelList.isEmpty()) return null;

        try {
            Set<NotificationChannels> parsedChannels = channelList.stream()
                    .map(String::trim)
                    .map(String::toUpperCase)
                    .map(NotificationChannels::valueOf)
                    .collect(Collectors.toSet());
            return EnumSet.copyOf(parsedChannels);
        } catch (Exception e) {
            log.error("Failed to parse user channels, reason: " + e.getMessage());
            return null;
        }
    }
}
