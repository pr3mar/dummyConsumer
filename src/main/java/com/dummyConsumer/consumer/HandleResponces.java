package com.dummyConsumer.consumer;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

class HandleResponces {

    static Response getResponse(WebTarget service) {
        Response response;
        try {
            response = service.request().get();
        } catch (ProcessingException e) {
            return null;
        }
        return response;
    }
}
