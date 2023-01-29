package binance.webapp.binanceApp;

import binance.webapp.binanceApp.util.UrlHandler;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;

import java.io.IOException;
import java.util.ArrayList;

@Route("")
public class MainView extends VerticalLayout {

    private KlineRepository repository;
    private TextField unwantedPairs = new TextField("Enter unwanted pairs");
    private Binder<Kline> binder = new Binder<>(Kline.class);
    private Grid<Kline> grid = new Grid<>(Kline.class);
    public MainView(KlineRepository repository) {
        this.repository = repository;

        grid.setColumns("pair", "current");
        add(getForm(), grid);
        refreshGrid();
    }

    private Component getForm() {
        var layout = new HorizontalLayout();
        layout.setAlignItems(Alignment.BASELINE);

        var requestButton = new Button("Request");
        requestButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        requestButton.addClickShortcut(Key.ENTER);

        layout.add(unwantedPairs, requestButton);

        //binder.bindInstanceFields(this);

        requestButton.addClickListener(buttonClickEvent -> {
            try {
                //var kline = new Kline();
                ArrayList<String> allPairs = UrlHandler.getAllWantedPairs(unwantedPairs.getValue());
                ArrayList<Kline> klines = UrlHandler.getAllKlines(allPairs);
                klines.forEach(kline -> {
                    repository.save(kline);
                });
                refreshGrid();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return layout;
    }

    private void refreshGrid() {
        grid.setItems(repository.findAll());
    }
}
