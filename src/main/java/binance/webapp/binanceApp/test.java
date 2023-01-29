//package binance.webapp.binanceApp;
//
//import com.vaadin.flow.component.Component;
//import com.vaadin.flow.component.Key;
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.button.ButtonVariant;
//import com.vaadin.flow.component.grid.Grid;
//import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.textfield.EmailField;
//import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.data.binder.Binder;
//import com.vaadin.flow.data.binder.ValidationException;
//import com.vaadin.flow.router.Route;
//
//@Route("")
//public class MainView extends VerticalLayout {
//
//    private PersonRepository repository;
//    private TextField firstname = new TextField("Enter unwanted pairs");
//    private TextField lastname = new TextField("Last name");
//    private EmailField email = new EmailField("Email");
//    private Binder<Person> binder = new Binder<>(Person.class);
//    private Grid<Person> grid = new Grid<>(Person.class);
//
//    public MainView(PersonRepository repository) {
//        this.repository = repository;
//
//        grid.setColumns("firstname", "lastname", "email");
//        add(getForm(), grid);
//        refreshGrid();
//    }
//
//    private Component getForm() {
//        var layout = new HorizontalLayout();
//        layout.setAlignItems(Alignment.BASELINE);
//
//        var addButton = new Button("Add");
//        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        addButton.addClickShortcut(Key.ENTER);
//
//        layout.add(firstname, lastname, email, addButton);
//
//        binder.bindInstanceFields(this);
//
//        addButton.addClickListener(buttonClickEvent -> {
//            try {
//                var person = new Person();
//                binder.writeBean(person);
//                repository.save(person);
//                binder.readBean(new Person());
//                refreshGrid();
//            } catch (ValidationException e) {
//                //Do nothing
//            }
//        });
//        return layout;
//    }
//
//    private void refreshGrid() {
//        grid.setItems(repository.findAll());
//    }
//}
//
