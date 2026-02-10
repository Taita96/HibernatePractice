package gm.carlos.app.model.repository;

import gm.carlos.app.model.entity.Bag;

import java.util.function.Consumer;

public interface ITransitionScreen {

    void goToBag();
    void goToDashboard();
    void goToSupplier();
    void goToLocation();
    void goToBagWithBag(Bag bag);
    void navigateControl(Consumer<ITransitionScreen> accion);
    void goScreen(Consumer<ITransitionScreen> accion); // recordatorio: probar usar Consumer para pasar contexto
    void confirmNavigation(Object object, Consumer<ITransitionScreen> accion);
}
