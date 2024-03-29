package com.smartbusiness.tmawarehouse.views.list;


import com.smartbusiness.tmawarehouse.model.entity.ItemEntity;
import com.smartbusiness.tmawarehouse.model.entity.ItemGroup;
import com.smartbusiness.tmawarehouse.model.entity.RequestEntity;
import com.smartbusiness.tmawarehouse.model.entity.UnitOfMeasurement;
import com.smartbusiness.tmawarehouse.service.ItemService;
import com.smartbusiness.tmawarehouse.service.RequestService;
import com.smartbusiness.tmawarehouse.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value="request", layout = MainLayout.class)
@PageTitle("Request List")

public class RequestListView extends VerticalLayout {
    Grid<RequestEntity> grid = new Grid<>(RequestEntity.class);
    TextField filterText = new TextField();
    RequestForm requestForm;
    private final RequestService requestService;
    private final ItemService itemService;


    public RequestListView(RequestService requestService, ItemService itemService) {
        this.requestService = requestService;
        this.itemService = itemService;
        addClassName("request-view");
        setSizeFull();
        configureRequestGrid();
        configureRequestForm();


        add(
                getToolbar(),
                getContent()
        );
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        requestForm.setRequestEntity(null);
        requestForm.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(requestService.findAllRequests(filterText.getValue()));
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, requestForm);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, requestForm);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureRequestForm() {
        requestForm = new RequestForm(itemService.findAllItems());
        requestForm.setWidth("25em");

        requestForm.addListener(RequestForm.AcceptEvent.class, this::acceptRequest);
        requestForm.addListener(RequestForm.SaveEvent.class, this::confirmRequest);
        requestForm.addListener(RequestForm.DeleteEvent.class, this::rejectRequest);
        requestForm.addListener(RequestForm.CloseEvent.class, e-> closeEditor());
    }

    private void acceptRequest(RequestForm.AcceptEvent acceptEvent) {
        if(itemService.canUpdateQuantity(acceptEvent.getRequest())){
            itemService.updateQuantity(acceptEvent.getRequest());
            requestService.updateRequestStatus(acceptEvent.getRequest(), "ACCEPTED");
            Notification.show("Request accepted!");
            updateList();
            closeEditor();
        }else{
            Notification.show("Request quantity is too high, can not update!");
            closeEditor();
        }

    }

    private void rejectRequest(RequestForm.DeleteEvent deleteEvent) {
        requestService.updateRequestStatus(deleteEvent.getRequest(), "REJECTED");
        Notification.show("Request rejected!");
        updateList();
        closeEditor();
    }

    private void confirmRequest(RequestForm.SaveEvent saveEvent) {


        requestService.saveRequest(saveEvent.getRequest());

        updateList();
        closeEditor();
    }

    private void configureRequestGrid() {
        grid.addClassNames("requests-grid");
        grid.setSizeFull();

        grid.setColumns("requestId","employeeName");
        grid.addColumn(request -> request.getItemEntity().getItemId()).setHeader("Item id").setSortable(true);
        grid.addColumn(r -> r.getUnitOfMeasurement().name()).setHeader("Unit of").setSortable(true);
        grid.addColumn(RequestEntity::getQuantity).setHeader("Quantity").setSortable(true);
        grid.addColumn(RequestEntity::getPriceUAH).setHeader("Price UAH").setSortable(true);
        grid.addColumn(RequestEntity::getComment).setHeader("comment").setSortable(true);
        grid.addColumn(RequestEntity::getStatus).setHeader("status").setSortable(true);


        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editRequest(e.getValue()));
    }

    private void editRequest(RequestEntity request) {
        if (request == null){
            closeEditor();
        }else{
            requestForm.setRequestEntity(request);
            requestForm.setVisible(true);
            addClassName("editing");
        }
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by request id...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(change -> updateList());

        Button addRequestButton = new Button("Add request");
        addRequestButton.addClickListener(e -> addRequest());

        var toolbar = new HorizontalLayout(filterText, addRequestButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

     void addRequest() {
        grid.asSingleSelect().clear();
        editRequest(new RequestEntity());
    }
}

