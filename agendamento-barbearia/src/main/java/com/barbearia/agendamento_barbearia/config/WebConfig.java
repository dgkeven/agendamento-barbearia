package com.barbearia.agendamento_barbearia.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Faz a raiz da aplicacao abrir a tela JSF.
 *
 * <p>O FacesServlet responde por "*.xhtml" (mapeamento por extensao, que tem
 * precedencia sobre o "/" do DispatcherServlet), entao basta redirecionar "/"
 * para a pagina de agendamento.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/agendamento.xhtml");
    }
}
