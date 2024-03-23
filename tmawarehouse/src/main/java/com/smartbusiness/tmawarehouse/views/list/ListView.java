package com.smartbusiness.tmawarehouse.views.list;


import com.smartbusiness.tmawarehouse.model.entity.ItemEntity;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("TMA warehouse")
@Route(value = "")
public class ListView extends VerticalLayout {
    Grid<ItemEntity> grid = new Grid<>(ItemEntity.class);
    TextField filterText = new TextField();
    ItemForm itemForm;


    public ListView() {
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureItemForm();


        add(getToolbar(), getContent());
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, itemForm);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, itemForm);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureItemForm() {
        itemForm = new ItemForm();
        itemForm.setWidth("25em");
    }

    private void configureGrid() {
        grid.addClassNames("items-grid");
        grid.setSizeFull();
        grid.setColumns("itemId", "itemGroup", "unitOfMeasurement", "quantity", "priceUAH", "status", "storageLocation", "contactPerson");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by item group...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        Button addItemButton = new Button("Add item");

        var toolbar = new HorizontalLayout(filterText, addItemButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }
}

