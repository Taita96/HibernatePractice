package gm.carlos.app.model.repository;

import gm.carlos.app.model.entity.Bag;

import java.util.function.Consumer;

/**
 * Interfaz que define la navegación entre pantallas en la aplicación.
 *
 * <p>Proporciona métodos para cambiar entre vistas como Bag, Dashboard, Supplier y Location,
 * así como métodos que permiten pasar contexto o ejecutar acciones durante la navegación.</p>
 *
 * <p>Se puede implementar en controladores que gestionen la interfaz gráfica
 * para centralizar la lógica de transición de pantallas.</p>
 */
public interface ITransitionScreen {

    /**
     * Navega a la pantalla de gestión de {@link Bag}.
     */
    void goToBag();


    /**
     * Navega a la pantalla principal (Dashboard).
     */
    void goToDashboard();

    /**
     * Navega a la pantalla de gestión de {@link gm.carlos.app.model.entity.Supplier}.
     */
    void goToSupplier();

    /**
     * Navega a la pantalla de gestión de {@link gm.carlos.app.model.entity.Location}.
     */
    void goToLocation();

    /**
     * Navega a la pantalla de Bag pasando un objeto {@link Bag} como contexto.
     *
     * @param bag objeto {@code Bag} que se quiere mostrar o editar.
     */
    void goToBagWithBag(Bag bag);

    /**
     * Ejecuta una acción con acceso al contexto de navegación.
     *
     * @param accion acción que recibe la interfaz {@code ITransitionScreen}.
     */
    void navigateControl(Consumer<ITransitionScreen> accion);

    /**
     * Navega a una pantalla y ejecuta una acción con contexto de navegación.
     *
     * @param accion acción que recibe la interfaz {@code ITransitionScreen}.
     */
    void goScreen(Consumer<ITransitionScreen> accion); // recordatorio: probar usar Consumer para pasar contexto

    /**
     * Muestra un objeto de confirmación antes de ejecutar una acción de navegación.
     *
     * @param object objeto que se quiere confirmar (por ejemplo, datos a guardar o descartar).
     * @param accion acción que se ejecuta tras la confirmación.
     */
    void confirmNavigation(Object object, Consumer<ITransitionScreen> accion);
}
