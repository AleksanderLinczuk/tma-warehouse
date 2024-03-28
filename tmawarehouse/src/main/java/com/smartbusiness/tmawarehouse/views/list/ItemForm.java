package com.smartbusiness.tmawarehouse.views.list;


import com.smartbusiness.tmawarehouse.model.entity.ItemEntity;
import com.smartbusiness.tmawarehouse.model.entity.ItemGroup;
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

public class ItemForm extends FormLayout {



    Binder<ItemEntity> itemBinder = new BeanValidationBinder<>(ItemEntity.class);

    ComboBox<ItemGroup> itemGroup = new ComboBox<>("Item Group");
    ComboBox<UnitOfMeasurement> unitOfMeasurement = new ComboBox<>("Unit Of Measurement");

    IntegerField quantity = new IntegerField("Quantity");
    NumberField priceUAH = new NumberField("Price UAH");
    TextField status = new TextField("Status");
    TextField storageLocation = new TextField("Storage Location");
    TextField contactPerson = new TextField("Contact Person");


    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");
    private ItemEntity itemEntity;

    public ItemForm() {
        addClassName("item-form");
        itemBinder.bindInstanceFields(this);

        itemGroup.setItems(ItemGroup.values());
        itemGroup.setItemLabelGenerator(Enum::name);

        unitOfMeasurement.setItems(UnitOfMeasurement.values());
        unitOfMeasurement.setItemLabelGenerator(Enum::name);


        add(
                itemGroup,
                unitOfMeasurement,
                quantity,
                priceUAH,
                status,
                storageLocation,
                contactPerson,
                createButtonsLayout());
    }


    public void setItemEntity(ItemEntity itemEntity) {
        this.itemEntity = itemEntity;
        itemBinder.readBean(itemEntity);

    }


    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);


        save.addClickListener(e -> validateAndSave());
        delete.addClickListener(e -> fireEvent(new DeleteEvent(this, itemEntity)));
        cancel.addClickListener(e -> fireEvent(new CloseEvent(this)));


        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        itemBinder.addStatusChangeListener(e -> save.setEnabled(itemBinder.isValid()));


        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try{
            itemBinder.writeBean(itemEntity);
            fireEvent(new SaveEvent(this, itemEntity));
        }catch(ValidationException e){
            e.printStackTrace();
        }
    }


    // Events
    public static abstract class ItemFormEvent extends ComponentEvent<ItemForm> {
        private ItemEntity item;

        protected ItemFormEvent(ItemForm source, ItemEntity item) {
            super(source, false);
            this.item = item;
        }

        public ItemEntity getItem() {
            return item;
        }
    }

    public static class SaveEvent extends ItemFormEvent {
        SaveEvent(ItemForm source, ItemEntity item) {
            super(source, item);
        }
    }

    public static class DeleteEvent extends ItemFormEvent {
        DeleteEvent(ItemForm source, ItemEntity item) {
            super(source, item);
        }

    }

    public static class CloseEvent extends ItemFormEvent {
        CloseEvent(ItemForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T>listener){
        return getEventBus().addListener(eventType, listener);
    }

 /*   public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
        return addListener(DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }

    public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
        return addListener(CloseEvent.class, listener);
    }*/

}