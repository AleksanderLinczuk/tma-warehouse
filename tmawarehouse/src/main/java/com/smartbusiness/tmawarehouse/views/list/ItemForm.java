package com.smartbusiness.tmawarehouse.views.list;


import com.smartbusiness.tmawarehouse.model.entity.ItemGroup;
import com.smartbusiness.tmawarehouse.model.entity.UnitOfMeasurement;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

public class ItemForm extends FormLayout {

    ComboBox<ItemGroup> itemGroup = new ComboBox<>("Item Group");
    ComboBox<UnitOfMeasurement> unitOfMeasurement = new ComboBox<>("Unit Of Measurement");

    NumberField quantity = new NumberField("Quantity");
    NumberField priceUAH = new NumberField("Price UAH");
    TextField status = new TextField("Status");
    TextField storageLocation = new TextField("Storage Location");
    TextField contactPerson = new TextField("Contact Person");


    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    public ItemForm() {
        addClassName("item-form");


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

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, close);
    }
}