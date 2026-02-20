package gm.carlos.app.model;


import gm.carlos.app.model.service.*;

/**
 * Clase central del modelo de la aplicación.
 *
 * <p>Actúa como contenedor de todos los servicios de negocio del sistema,
 * proporcionando un único punto de acceso a la capa lógica desde los controladores.</p>
 *
 * <p>Esta clase evita que los controladores creen directamente instancias de los servicios,
 * favoreciendo la centralización, el desacoplamiento y la mantenibilidad del sistema.</p>
 *
 * <p>Implementa un patrón Service Locator simplificado dentro de la arquitectura MVC:
 * <ul>
 *     <li>Los controladores acceden al modelo.</li>
 *     <li>El modelo expone los servicios.</li>
 *     <li>Los servicios gestionan la lógica y delegan en los DAO.</li>
 * </ul>
 * </p>
 *
 * <p>No contiene lógica de negocio ni acceso directo a la base de datos.</p>
 *
 * @author Carlos
 * @version 1.0
 */
public class Model {

    private SupplierService supplierService;
    private LocationService locationService;
    private BagService bagService;
    private StockService stockService;
    private TechnicalSheetService technicalSheetService;
    private LocationBagService locationBagService;

    /**
     * Inicializa todos los servicios del sistema.
     *
     * <p>Los servicios se crean una única vez y se reutilizan durante todo
     * el ciclo de vida de la aplicación.</p>
     */
    public Model(){
        supplierService = new SupplierService();
        locationService = new LocationService();
        bagService = new BagService();
        stockService = new StockService();
        technicalSheetService = new TechnicalSheetService();
        locationBagService = new LocationBagService();
    }

    /**
     * Obtiene el servicio de gestión de proveedores.
     *
     * @return instancia de {@link SupplierService}
     */
    public SupplierService getSupplierService() {
        return supplierService;
    }

    /**
     * Obtiene el servicio de gestión de ubicaciones.
     *
     * @return instancia de {@link LocationService}
     */
    public LocationService getLocationService() {
        return locationService;
    }

    /**
     * Obtiene el servicio de gestión de bolsas.
     *
     * @return instancia de {@link BagService}
     */
    public BagService getBagService() {
        return bagService;
    }

    /**
     * Obtiene el servicio de gestión de stock.
     *
     * @return instancia de {@link StockService}
     */
    public StockService getStockService() {
        return stockService;
    }

    /**
     * Obtiene el servicio de gestión de fichas técnicas.
     *
     * @return instancia de {@link TechnicalSheetService}
     */
    public TechnicalSheetService getTechnicalSheetService() {
        return technicalSheetService;
    }

    /**
     * Obtiene el servicio de relación entre bolsas y ubicaciones.
     *
     * @return instancia de {@link LocationBagService}
     */
    public LocationBagService getLocationBagService() {
        return locationBagService;
    }

}
