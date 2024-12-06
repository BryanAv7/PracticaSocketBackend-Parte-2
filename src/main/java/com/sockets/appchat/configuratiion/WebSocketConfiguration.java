package com.sockets.appchat.configuratiion;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker //Nos permite configurar un Broker para la comunicación con los clientes.
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    //Este método nos permite habilitar broker: : Enrutador de SMS entre CLI Y SV
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //Se habilita el broker y se especifica el path por el cual se ingresara al broker (enviar-recibir SMS)
        registry.enableSimpleBroker("/topic");

        //Establecemos un path de destino de los mensajes, por donde la aplicación va a estar destinando
        //los mensajes.
        registry.setApplicationDestinationPrefixes("/app");
    }


    //Este método nos permite registrar los endpoints:  Punto Acceso del sv en la cual los CLI pueden comunicarse
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //Especificamos el path por el cual el frontend se conectara al servidor socket
        //En setAllowedOrigins: Permite solicitudes desde http://localhost:4200
        //withSockJS: La conexión va a hacer por una librería de JS, que se utiliza en el frontend.
        registry.addEndpoint("/chat-socket").setAllowedOrigins("/http://localhost:4200").withSockJS();
    }

}
