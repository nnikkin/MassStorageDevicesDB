package com.nikkin.devicesdb.Views.Pages;

import com.nikkin.devicesdb.Views.BaseAppView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.servlet.http.HttpServletResponse;

@Route("error")
public class ErrorView extends BaseAppView implements HasErrorParameter<Exception> {
    private VerticalLayout content;
    private H1 errorTitle;
    private H2 errorMessage;
    private H2 errorDetails;

    public ErrorView() {
        super("Ошибка");

        content = new VerticalLayout();
        content.setWidthFull();
        content.setAlignItems(VerticalLayout.Alignment.CENTER);
        content.setJustifyContentMode(VerticalLayout.JustifyContentMode.CENTER);
        content.getStyle()
                .set("min-height", "60vh")
                .set("text-align", "center")
                .set("padding", "var(--lumo-space-l)");

        errorTitle = new H1();
        errorMessage = new H2();
        errorDetails = new H2();

        content.add(errorTitle, errorMessage, errorDetails);
        setContent(content);
    }

    public ErrorView(String title) {
        super(title);
    }

    @Override
    public int setErrorParameter(BeforeEnterEvent event,
                                 ErrorParameter<Exception> parameter) {
        String path = event.getLocation().getPath();
        if (path != null && !path.isEmpty()) {
            errorDetails.setText("Путь: /" + path);
        } else {
            errorDetails.setVisible(false);
        }

        Exception exception = parameter.getException();
        if (exception instanceof NotFoundException) {
            errorTitle.setText("404 - Страница не найдена");
            errorMessage.setText("К сожалению, запрошенная страница не существует.");
            return HttpServletResponse.SC_NOT_FOUND;
        } else {
            errorTitle.setText("Ошибка");
            errorMessage.setText("Произошла ошибка при обработке запроса.");
            return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }

            //case HttpServletResponse.SC_FORBIDDEN:
            //    errorTitle.setText("403 - Доступ запрещён");
             //   errorMessage.setText("У вас нет прав для доступа к этому ресурсу.");
             //   break;

           // case HttpServletResponse.SC_INTERNAL_SERVER_ERROR:
             //   errorTitle.setText("500 - Внутренняя ошибка сервера");
            //    errorMessage.setText("Произошла непредвиденная ошибка. Пожалуйста, попробуйте позже.");
             //   break;

    }
}