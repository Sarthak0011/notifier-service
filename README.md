# 📢 Notifier Service

A generic, scalable **Notification Microservice** built using Spring Boot and Kafka. It listens to events and sends notifications through configurable channels like **Email**, **Telegram**, and more — based on payload and channel preference.

---

## 🚀 Features

- Kafka-based event-driven architecture
- Dynamic template system for different event types
- Multi-channel support (email, telegram, etc.)
- Plug-and-play support for new events and channels
- Environment variable–driven configuration
- Built-in retry and fallback support

---

## 🔧 Configuration

Sensitive data like email credentials and Kafka configs should be passed via **environment variables**:

```env
SPRING_MAIL_USERNAME=your_email@gmail.com
SPRING_MAIL_PASSWORD=your_password
SPRING_KAFKA_BOOTSTRAP_SERVERS=localhost:9092
```

---

## Sample Producer Payload
```declarative
{
  "event": "UserSignUp",
  "timestamp": "2025-08-01T18:45:00Z",
  "enabledChannels": ["EMAIL", "TELEGRAM"],
  "metadata": {
    "email": "john.doe@example.com",
    "subject": "Welcome to SpeedBot",
    "message": "Thanks for signing up, John!",
    "user": "john.doe"
  }
}
```

---

## ➕ How to Add More Notification Channels?
Just implement the NotificationSender interface and mark it as a Spring component:
```declarative
@Component
public class SmsSender implements NotificationSender {
    @Override
    public boolean supports(NotificationChannels channel) {
        return channel == NotificationChannels.SMS;
    }

    @Override
    public void send(String message, Event event) {
        // SMS logic here
    }
}
```

---

## ➕ How to Add More Events?
1. Extend NotificationServiceTemplate:
```declarative
public interface UserSignUpTemplate extends NotificationServiceTemplate {
    // Optional: custom methods
}
```

2. Implement the Template:
```declarative
@Service
public class UserSignUpTemplateImpl implements UserSignUpTemplate {
    @Override
    public boolean supports(Event event) {
        return "UserSignUp".equals(event.getEvent());
    }

    @Override
    public String render(Event event) {
        return "Welcome " + event.getMetadata().get("user") + "!";
    }
}
```