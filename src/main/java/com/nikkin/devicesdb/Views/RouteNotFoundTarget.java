package com.nikkin.devicesdb.Views;

import com.nikkin.devicesdb.Views.Pages.ErrorView;
import com.vaadin.flow.router.*;
import jakarta.servlet.http.HttpServletResponse;

public class RouteNotFoundTarget extends RouteNotFoundError {

    @Override
    public int setErrorParameter(BeforeEnterEvent event,
                                 ErrorParameter<NotFoundException> parameter) {

        event.forwardTo(ErrorView.class);
        return HttpServletResponse.SC_NOT_FOUND;
    }
}
