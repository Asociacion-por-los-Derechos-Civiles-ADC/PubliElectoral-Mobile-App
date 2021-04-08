## adc-mobile-app | PubliElectoral

Es un proyecto desarrollado para la [ADC](https://adc.org.ar/)

PubliElectoral mobile es una aplicación para celulares llevada a cabo por la Asociación por los Derechos Civiles (ADC) y la Cooperativa Tecnológica Cambá de la Argentina.

La aplicación puede ser utilizada en celulares que cuentan con Android como sistema operativo.
El objetivo del proyecto es llevar un monitoreo de las publicidades políticas en épocas de campaña electoral que aparecen en el feed de noticias de Facebook de una persona usuaria orgánica que decide instalarse la app para colaborar con el proyecto. 

Para ello, se uso como base un wrapper (agregar una capa extra a la aplicación original de Facebook) para poder hacer la detecciones sin tener que violar la seguridad de la aplicación de original de Facebook. Nos basamos en la aplicación ya desarrollada [Frost-for-Facebook](https://github.com/AllanWang/Frost-for-Facebook) que ya nos proveía esa capa extra, ademas de hacer valer la importacia del Software Libre.

PubliElectoral no almacena información personal de las personas usuarias de ningún tipo. Lo que archivamos es información de los avisos a analizar. La misma se encontrará alojada en servidores propios.
