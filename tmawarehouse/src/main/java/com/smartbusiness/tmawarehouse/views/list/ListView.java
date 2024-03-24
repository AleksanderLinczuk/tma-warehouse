package com.smartbusiness.tmawarehouse.views.list;


import com.smartbusiness.tmawarehouse.model.entity.ItemEntity;
import com.smartbusiness.tmawarehouse.service.ItemService;
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
    private ItemService itemService;





    public ListView(ItemService itemService) {
        this.itemService = itemService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureItemForm();


        add(getToolbar(), getContent());
        
        updateList();
    }

    private void updateList() {
        grid.setItems(itemService.findAllItems(filterText.getValue()));
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
        filterText.addValueChangeListener(change -> updateList());

        Button addItemButton = new Button("Filter by item group");

        var toolbar = new HorizontalLayout(filterText, addItemButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }
}

