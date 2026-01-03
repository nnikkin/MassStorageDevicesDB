package com.nikkin.devicesdb.Views;

import com.nikkin.devicesdb.Views.Pages.ErrorPageView;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.router.RouteNotFoundError;
import jakarta.servlet.http.HttpServletResponse;

public class RouteNotFoundTarget extends RouteNotFoundError {

    @Override
    public int setErrorParameter(BeforeEnterEvent event,
                                 ErrorParameter<NotFoundException> parameter) {

        // Перенаправить на главную или специальную страницу ошибки
        event.forwardTo(ErrorPageView.class);
        return HttpServletResponse.SC_NOT_FOUND;
    }
}
