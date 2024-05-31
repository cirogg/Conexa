# Conexa Android Challenge

Proyecto dedicado a la prueba técnica para [Conexa](https://conexa.ai/).

## Tabla de Contenidos

- [Descripción](#descripción)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Aclaraciones](#aclaraciones)
- [Instalación](#instalación)
- [Contacto](#contacto)
- [Referencias](#referencias)

## Descripción

Aplicación que consume una API de ejemplo. Devuelve Noticias y Usuarios. Navegación entre pantallas con sus respectivos detalles y geolocalización en mapa. 
Todo usando Jetpack Compose, Kotlin, Mvvm y corutinas.
Tests incluidos.

## Tecnologías Utilizadas

Este proyecto fue construido utilizando las siguientes tecnologías:

- [Kotlin](https://kotlinlang.org/) - Lenguaje de programación principal
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Framework de UI
- [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) - Para programación asincrónica
- [Retrofit](https://square.github.io/retrofit/) - Cliente HTTP para Android y Java
- [Room](https://developer.android.com/jetpack/androidx/releases/room) - Librería de base de datos
- [Dagger/Hilt](https://dagger.dev/hilt/) - Inyección de dependencias
- [Material Design](https://material.io/design) - Principios de diseño
- [Coil](https://coil-kt.github.io/coil/) - Librería para carga de imágenes
- [Compose Navigation ALPHA](https://developer.android.com/reference/androidx/navigation/package-summary) - Librería nueva de Compose navigation

## Aclaraciones

Para la navegacion usé una nueva versión de la librería que todavía esta en alpha/beta, fue presentada hace poco en la Google I/O. Aproveché para usarla y ver sus bondades. Adios a las rutas, cadenas de texto y list of arguments.
En el manifest esta hardcodeada la Api Key de google maps, no es lo correcto, deberia ir en un repo de secrets de github.
Perdón por la pobre propuesta de UX/UI, dios bendiga a los diseñadores.

Entre HILT y KOIN fui por HILT primero que nada porque es con la que mas cómodo me siento y en segundo lugar porque prefiero que los errores aparezcan en tiempo de compilación y no en tiempo de ejecución como lo hace KOIN.

La data que la API devuelve, tanto de noticias como de usuarios, son almacenadas con ROOM en el dispositivo cada vez que se consume. En caso de encontrar algun conflicto la sobreescribe.
Para mostrar el detalle simplemente navego comunicando un ID y recupero de ROOM el objeto entero para luego mostrarlo. No hay navegacion con Serializables. Primero porque no es lo recomendado y segundo porque la librería de navegación todavía no esta tan preparada para usar Serializables. (Siempre se puede mandar un json como string igualmente).

La app, con el dolor de ojos correspondiente, es responsiva. También contempla el modo landscape. Los layouts son todos iguales en ambas direcciones salvo el del detalle del usuario. Ahí cambia la distribución para aprovechar mas el espacio.

También aproveché y usé "Version catalog" para el manejo de dependencias.

La splash screen esta hecha con la propia API de Splash screen de android.

## Instalación

Instrucciones para configurar el proyecto localmente.

1. Clona el repositorio:
   ```bash
   git clone https://github.com/cirogg/Conexa.git

2. Navega al directorio del proyecto:
   ```bash
   cd Conexa
   
3. Abre el proyecto en tu IDE favorito (recomendado: Android Studio).
4. Asegúrate de tener configurado un dispositivo/emulador Android.

## Contacto
Ciro Gonzalez - ciroGonzalez@outlook.com

## Referencias

Zully Moncayo, ex lider: https://www.linkedin.com/in/zully-moncayo/

Santiago Lorenzo, CTO & Founder: https://www.linkedin.com/in/santiagolorenzo/ 

Nicolas Peralta, CTO: https://www.linkedin.com/in/nicolasperaltait/
 
