package com.example.cotizaciondolar.models;

import static com.example.cotizaciondolar.models.entities.EventType.SMS_SENT;

import android.content.Context;

import com.example.cotizaciondolar.contracts.CodeContract;
import com.example.cotizaciondolar.models.entities.EventRequest;
import com.example.cotizaciondolar.services.EventService;

import java.util.Random;

public class CodeModel implements CodeContract.Model {
    private final Context context;
    private EventService eventService;
    private String activeCode;

    public CodeModel(Context context) {
        this.context = context;
        this.generateNewCode();
    }

    @Override
    public String getActiveCode() {
        return activeCode;
    }

    @Override
    public String generateNewCode() {
        Random random = new Random();
        int code = random.nextInt(10000);
        this.activeCode = String.format("%04d", code);

        // Genera un evento de SMS enviado
        EventRequest smsEvent = new EventRequest(
                SMS_SENT,
                "Código doble factor enviado por SMS"
        );

        eventService = new EventService(context);
        eventService.execute(smsEvent);

        return this.activeCode;
    }
}
