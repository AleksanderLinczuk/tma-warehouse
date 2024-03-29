package com.smartbusiness.tmawarehouse.views.list;


import com.smartbusiness.tmawarehouse.model.entity.ItemEntity;
import com.smartbusiness.tmawarehouse.model.entity.RequestEntity;
import com.smartbusiness.tmawarehouse.model.entity.UnitOfMeasurement;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RequestForm extends FormLayout {



    Binder<RequestEntity> requestBinder = new BeanValidationBinder<>(RequestEntity.class);




    TextField employeeName = new TextField("Employee name");
    ComboBox<ItemEntity> itemEntity = new ComboBox<>("Item id");
    ComboBox<UnitOfMeasurement> unitOfMeasurement = new ComboBox<>("Unit Of Measurement");
    IntegerField quantity = new IntegerField("Quantity");
    NumberField priceUAH = new NumberField("Price UAH");
    TextField comment = new TextField("Comment");


    Button accept = new Button("Accept");
    Button save = new Button("Save");
    Button reject = new Button("Reject");
    Button cancel = new Button("Cancel");
    private RequestEntity requestEntity;

    public RequestForm(List<ItemEntity> items) {
        addClassName("request-form");
        requestBinder.bindInstanceFields(this);

        itemEntity.setItems(items);
        itemEntity.setItemLabelGenerator(i -> String.valueOf(i.getItemId()));

        unitOfMeasurement.setItems(UnitOfMeasurement.values());
        unitOfMeasurement.setItemLabelGenerator(Enum::name);



        add(
                employeeName,
                itemEntity,
                unitOfMeasurement,
                quantity,
                priceUAH,
                comment,

                createButtonsLayout());
    }


    public void setRequestEntity(RequestEntity requestEntity) {
        this.requestEntity = requestEntity;
        requestBinder.readBean(requestEntity);

    }


    private HorizontalLayout createButtonsLayout() {
        accept.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        reject.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);


        accept.addClickListener(e -> fireEvent(new AcceptEvent(this,requestEntity)));
        save.addClickListener(e -> validateAndSave());
        reject.addClickListener(e -> fireEvent(new DeleteEvent(this, requestEntity)));
        cancel.addClickListener(e -> fireEvent(new CloseEvent(this)));


        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        requestBinder.addStatusChangeListener(e -> save.setEnabled(requestBinder.isValid()));


        return new HorizontalLayout(accept, save, reject, cancel);
    }

    private void validateAndSave() {
        try{
            requestBinder.writeBean(requestEntity);
            fireEvent(new SaveEvent(this, requestEntity));


        }catch(ValidationException e){
            e.printStackTrace();
        }
    }


    // Events
    public static abstract class RequestFormEvent extends ComponentEvent<RequestForm> {
        private RequestEntity request;

        protected RequestFormEvent(RequestForm source, RequestEntity request) {
            super(source, false);
            this.request = request;
        }

        public RequestEntity getRequest() {
            return request;
        }
    }

    public static class SaveEvent extends RequestFormEvent {
        SaveEvent(RequestForm source, RequestEntity request) {
            super(source, request);
        }
    }

    public static class AcceptEvent extends RequestFormEvent {
        AcceptEvent(RequestForm source, RequestEntity request) {
            super(source, request);
        }
    }

    public static class DeleteEvent extends RequestFormEvent {
        DeleteEvent(RequestForm source, RequestEntity request) {
            super(source, request);
        }

    }

    public static class CloseEvent extends RequestFormEvent {
        CloseEvent(RequestForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T>listener){
        return getEventBus().addListener(eventType, listener);
    }



}