package com.smartbusiness.tmawarehouse.views.list;


import com.smartbusiness.tmawarehouse.model.entity.ItemEntity;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;

@PageTitle("TMA warehouse")
@Route(value = "")
public class ListView extends VerticalLayout {
    Grid<ItemEntity> grid = new Grid<>(ItemEntity.class);
    TextField filterText = new TextField();

    public ListView() {
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(getToolbar(), grid);
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

