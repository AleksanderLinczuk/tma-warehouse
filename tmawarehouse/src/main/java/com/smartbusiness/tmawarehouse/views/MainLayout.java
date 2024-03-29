package com.smartbusiness.tmawarehouse.views;


import com.smartbusiness.tmawarehouse.views.list.ItemListView;
import com.smartbusiness.tmawarehouse.views.list.RequestListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("TMA Warehouse Test Task");
        logo.addClassNames(
                LumoUtility.FontSize.LARGE,
                LumoUtility.Margin.MEDIUM);

        var header = new HorizontalLayout(new DrawerToggle(), logo );

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        header.addClassNames(
                LumoUtility.Padding.Vertical.NONE,
                LumoUtility.Padding.Horizontal.MEDIUM);

        addToNavbar(header);

    }

    private void createDrawer() {

        RouterLink requestList = new RouterLink("Request List", RequestListView.class);
        RouterLink itemList = new RouterLink("Item List", ItemListView.class);
        itemList.setHighlightCondition(HighlightConditions.sameLocation());
        requestList.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
                itemList,
                requestList

        ));
    }
}