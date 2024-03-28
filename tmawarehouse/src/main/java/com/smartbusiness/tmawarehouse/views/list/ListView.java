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


        add(
                getToolbar(),
                getContent()
        );
                updateList();
                closeEditor();
    }

    private void closeEditor() {
        itemForm.setItemEntity(null);
        itemForm.setVisible(false);
        removeClassName("editing");
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

        itemForm.addListener(ItemForm.SaveEvent.class, this::saveItem);
        itemForm.addListener(ItemForm.DeleteEvent.class, this::deleteItem);
        itemForm.addListener(ItemForm.CloseEvent.class, e-> closeEditor());
    }

    private void deleteItem(ItemForm.DeleteEvent deleteEvent) {
        itemService.deleteItem(deleteEvent.getItem());
        updateList();
        closeEditor();
    }

    private void saveItem(ItemForm.SaveEvent saveEvent) {
        itemService.saveItem(saveEvent.getItem());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassNames("items-grid");
        grid.setSizeFull();
        grid.addColumn(item -> item.getItemGroup().name()).setHeader("Item group");
        grid.addColumn(item -> item.getUnitOfMeasurement().name()).setHeader("Unit");
        grid.setColumns("itemId","itemGroup","unitOfMeasurement","quantity", "priceUAH", "status", "storageLocation", "contactPerson");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editItem(e.getValue()));
    }

    private void editItem(ItemEntity item) {
        if (item == null){
            closeEditor();
        }else{
            itemForm.setItemEntity(item);
            itemForm.setVisible(true);
            addClassName("editing");
        }
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by item group...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(change -> updateList());

        Button addItemButton = new Button("Add item");
        addItemButton.addClickListener(e -> addItem());

        var toolbar = new HorizontalLayout(filterText, addItemButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addItem() {
        grid.asSingleSelect().clear();
        editItem(new ItemEntity());
    }
}

