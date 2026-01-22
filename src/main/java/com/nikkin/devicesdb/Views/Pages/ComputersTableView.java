package com.nikkin.devicesdb.Views.Pages;

import com.nikkin.devicesdb.Dto.*;
import com.nikkin.devicesdb.Services.ComputerService;
import com.nikkin.devicesdb.Views.BaseForm;
import com.nikkin.devicesdb.Views.BaseTableView;
import com.vaadin.flow.component.grid.ColumnRendering;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@Route("computers")
final public class ComputersTableView extends BaseTableView<ComputerDto> {
    public ComputersTableView(ComputerService service) {
        super("Компьютеры", service);
    }

    @Override
    protected BaseForm<ComputerDto> createForm() {
        return new ComputerForm();
    }

    @Override
    protected void initTable() {
        grid = new Grid<>(ComputerDto.class, false);

        grid.setItems(new ArrayList<>());
        grid.setMultiSort(true, Grid.MultiSortPriority.APPEND);
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.setColumnRendering(ColumnRendering.LAZY);
        grid.setEmptyStateText("В таблице отсутствуют записи.");

        grid.addColumn(ComputerDto::name)
                .setHeader("Название")
                .setAutoWidth(true)
                .setSortable(true);

        grid.addColumn(dto -> formatDeviceSet(dto.linkedHddDtos(), "HDD"))
                .setHeader("HDD")
                .setAutoWidth(true)
                .setSortable(false);

        grid.addColumn(dto -> formatDeviceSet(dto.linkedSsdDtos(), "SSD"))
                .setHeader("SSD")
                .setAutoWidth(true)
                .setSortable(false);

        grid.addColumn(dto -> formatDeviceSet(dto.linkedRamDtos(), "RAM"))
                .setHeader("ОЗУ")
                .setAutoWidth(true)
                .setSortable(false);

        grid.addColumn(dto -> formatDeviceSet(dto.linkedFlashDtos(), "Flash"))
                .setHeader("Флеш-память")
                .setAutoWidth(true)
                .setSortable(false);

        grid.addSelectionListener(
                selectionEvent -> {
                    if (selectionEvent.getAllSelectedItems().isEmpty()) {
                        changeEditBtnState(false);
                        changeDelBtnState(false);
                    } else {
                        changeDelBtnState(true);
                        changeEditBtnState(true);
                    }
                }
        );
    }

    // Форматирование списка устройств для отображения
    private String formatDeviceSet(Set<?> devices, String type) {
        if (devices == null || devices.isEmpty()) {
            return "—";
        }

        return devices.size() + " шт.";
    }

    @Override
    protected void setupFilters() {
        var headerCells = grid.getHeaderRows()
                .getFirst()
                .getCells();

        ComputerFilter filter = new ComputerFilter(grid.getListDataView());

        headerCells.getFirst().setComponent(createFilterHeader("Название", filter::setName));
    }

    private static class ComputerFilter {
        private final GridListDataView<ComputerDto> dataView;
        private String name;

        public ComputerFilter(GridListDataView<ComputerDto> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }

        public void setName(String name) {
            this.name = name;
            this.dataView.refreshAll();
        }

        private boolean test(ComputerDto dto) {
            return matches(dto.name(), name);
        }

        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty()
                    || (value != null && value.toLowerCase().contains(searchTerm.toLowerCase()));
        }
    }

    static class ComputerForm extends BaseForm<ComputerDto> {
        private TextArea nameField;

        private Binder<ComputerDto> binder;
        private Integer currentId = null;

        public ComputerForm() {
            super();

            binder = new Binder<>(ComputerDto.class);
            setBinder(binder);

            setupFields();
            setupBinder();

            add(nameField);

            setResponsiveSteps(new ResponsiveStep("0", 1));
        }

        @Override
        protected void setDto(ComputerDto dto) {
            this.currentId = dto.id();
            binder.readBean(dto);
        }

        @Override
        protected void setupFields() {
            nameField = new TextArea("Название компьютера:");
            nameField.setMinRows(1);
            nameField.setMaxRows(1);
            nameField.setMinLength(1);
            nameField.setMaxLength(100);
            nameField.setClearButtonVisible(true);
            nameField.setRequiredIndicatorVisible(true);
        }

        @Override
        protected void setupBinder() {
            binder.forField(nameField)
                    .asRequired("Название обязательно")
                    .withValidator(name -> !name.isEmpty() && name.length() <= 100,
                            "Название должно быть от 1 до 100 символов")
                    .bind(
                            ComputerDto::name,
                            (dto, value) -> {}
                    );
        }

        @Override
        protected Optional<ComputerDto> getFormDataObject() {
            if (binder.validate().hasErrors()) {
                return Optional.empty();
            }

            ComputerDto dto = new ComputerDto(
                    currentId,
                    nameField.getValue(),
                    Set.of(),
                    Set.of(),
                    Set.of(),
                    Set.of()
            );

            return Optional.of(dto);
        }

        @Override
        protected void clearFields() {
            currentId = null;
            nameField.clear();
            binder.validate();
        }
    }
}