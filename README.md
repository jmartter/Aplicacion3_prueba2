# Ejercicio 3 Examen Eventos

Este proyecto es una aplicación de Android que muestra información sobre varias características en un mapa. La aplicación lee datos de un archivo JSON, convierte coordenadas UTM a latitud y longitud, y muestra la información en un formato fácil de usar.

## Repositorio

El código fuente de la aplicación se encuentra en el siguiente repositorio de GitHub: [Aplicacion3_prueba2](https://github.com/jmartter/Aplicacion3_prueba2.git)
## Clases

### CoordinateConverter

El objeto `CoordinateConverter` es responsable de convertir coordenadas UTM a latitud y longitud. Utiliza la biblioteca `proj4j` para realizar la transformación de coordenadas.

#### Métodos

- `utmToLatLon(easting: Double, northing: Double): Pair<Double, Double>`
  - Convierte coordenadas UTM (easting y northing) a latitud y longitud.
  - Devuelve un par de latitud y longitud.

### FeatureInfo

La clase de datos `FeatureInfo` contiene información sobre una característica.

#### Propiedades

- `title: String`
  - El título de la característica.
- `description: String`
  - La descripción de la característica.
- `phoneNumber: String`
  - El número de teléfono asociado con la característica.
- `latitude: Double`
  - La latitud de la característica.
- `longitude: Double`
  - La longitud de la característica.

### FeatureInfoAdapter

La clase `FeatureInfoAdapter` es responsable de crear vistas para mostrar la información de las características.

#### Métodos

- `createFeatureView(featureInfo: FeatureInfo): View`
  - Crea una vista para mostrar la información de un objeto `FeatureInfo`.
  - Devuelve una `View` que contiene la información de la característica.

### MainActivity

La clase `MainActivity` es el punto de entrada principal de la aplicación. Maneja la carga de los datos JSON, alterna la visibilidad de la lista de características y muestra la información de las características.

#### Métodos

- `onCreate(savedInstanceState: Bundle?)`
  - Inicializa la actividad, configura los elementos de la interfaz de usuario y lee los datos JSON desde los assets.
- `toggleSimpleInfo()`
  - Alterna la visibilidad de la lista de características.
- `showSimpleInfo()`
  - Muestra la información de las características creando vistas para cada característica y agregándolas al layout.

## Datos JSON

La aplicación lee datos de un archivo JSON ubicado en la carpeta `assets`. El archivo JSON contiene un array de características, cada una con propiedades como título, descripción y coordenadas.
