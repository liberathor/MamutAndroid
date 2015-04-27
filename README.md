# Mamut Android
    
Aplicacion android para el seguimiento y reporte de los estados de transporte pesado, en este caso la empresa Mamut.


### Sumary ###

* Esta aplicacion fue diseniada exclusivamente y probada unicamente en dipositivos Android PND Topicon
    mas info -> [Topicon](http://www.topicon.hk)
    
* No funcionara correctamente ni ofrecera soporte para telefonos, dispositivos de tipo "handset" o "tablets" de tipo estandar.

* El dispositivo se encargara de enviar informacion unicamente por puerto de tipo serial.

* Este dispositivo requerie de un adaptador USB-Serie

* Este repositorio no contiene informacion sobre el hardware utilizado

### Set up ###

* Configuracion:

    Antes de correr el proyecto es necesario disponer de un dispositivos Android PND Topicon y de un adaptador USB-RS232
    
    ![Alt text](http://www.topicon.hk/img/p/product/en_us/p2_img.jpg "Topicon device")
    
    ![Alt text](http://www.tri-plc.com/USB-RS232.jpg "USB-RS232 Converter")

1. Instale o actualize su IDE (Intellij IDEA, Android Studio, o compatible con gradle) y el Android ADT
2. Clone el repositorio MamutAndroid -> $ git clone https://github.com/widetechmobile/MamutAndroid
3. Clone el repositorio CoreSerialPort -> $ git clone https://github.com/widetechmobile/CoreSerialPort
4. Mueva o Copie la carpeta del repositorio clonado CoreSerialPort dentro de la carpeta del proyecto MamutAndroid
6. Abra el proyecto MamutAndroid, sincronize el proyecto gradle, compile y ejecute
7. Para realizar las pruebas por favor instale el driver de su adaptador USB-RS232 de la pagina del fabricante:

    - Para windows puede encontrar algunos drivers en: [Windows](http://www.tri-plc.com/USB-RS232/drivers.htm)
    - Para macos drivers en macos primero revise la documentacion del fabricante en, una referencia general la puede encontrar en: [Macos reference] (http://plugable.com/2011/07/12/installing-a-usb-serial-adapter-on-mac-os-x)
    - Se para las pruebas usaron especificamente los drivers de prolific en macos: [Prolific](http://www.prolific.com.tw/us/showproduct.aspx?p_id=229&pcid=41)
    
8. Instale una consola de depuracion de comunicacion con el dispositivo:
    - En las pruebas pruebas en macos se uso CoolTerm: [CoolTerm](http://freeware.the-meiers.org)

* Dependencies
    Favor revise el archivo de dependencias buld.gradle:
    
        * 'com.android.support:appcompat-v7:21.0.0'
        * 'com.android.support:gridlayout-v7:21.0.0'
        * 'com.android.support:support-v4:21.0.0'
        * project(':CoreSerialPort:CoreSerialPortApp')
        
### Desarrollado por ###

* Desarrolladores: 

    - Juan Carlos Ramirez Vargas: Interfaz, logica negocio
    - Edwin Andres Sandoval:    : Liberias de comunicacion
    - Desarrollado en [Widetech](http://widetech.co)

* [Learn Markdown](https://bitbucket.org/tutorials/markdowndemo)