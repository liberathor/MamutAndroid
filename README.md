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
2. Clone el repositorio MamutAndroid -> `$ git clone https://github.com/widetechmobile/MamutAndroid`
3. Clone el repositorio CoreSerialPort -> `$ git clone https://github.com/widetechmobile/CoreSerialPort`
4. Mueva o Copie la carpeta del repositorio clonado CoreSerialPort dentro de la carpeta del proyecto MamutAndroid
6. Abra el proyecto MamutAndroid, sincronize el proyecto gradle, compile y ejecute
7. Para realizar las pruebas por favor instale el driver de su adaptador USB-RS232 de la pagina del fabricante:

    - Para windows puede encontrar algunos drivers en: [Windows](http://www.tri-plc.com/USB-RS232/drivers.htm)
    - Para macos drivers en macos primero revise la documentacion del fabricante en, una referencia general la puede encontrar en: [Macos reference] (http://plugable.com/2011/07/12/installing-a-usb-serial-adapter-on-mac-os-x)
    - Se para las pruebas usaron especificamente los drivers de prolific en macos: [Prolific](http://www.prolific.com.tw/us/showproduct.aspx?p_id=229&pcid=41)
    
8. Instale una consola de depuracion de comunicacion con el dispositivo:
    - En las pruebas pruebas en macos se uso CoolTerm: [CoolTerm](http://freeware.the-meiers.org)

### Debug ####

#### TRAMAS DE COMUNICACION SERIAL APLICATION MAMUT ANDROID ####

    ESTRUCTURA BASICA:

    TRAMA DE ENVIO DEL DISPOSITIVO (TX):

    	>MDT,RXX,{INFO};{IDACK}<

    >		    —> INICIO TRAMA 		    (Obligatorio)
    MDT 	    —> ENCABEZADO			    (Obligatorio)
    RXX 		—> COMANDO BOTON		    (Obligatorio)
    {INFO};  	—> DATOS ADICIONALES 	    (Opcional)			El cual esta compuesto de uno o mas mensajes separados por comas, y finalizando con “;”, Ejem: “DATO1,DATO2,DATO3…;”
    {IDACK}	    —> ID DEL ACK 			    (Obligatorio) 		Numero de identificación de recepción del mensaje enviado por el dispositivo para ser confirmado posteriormente por el RX
    <		    —> FIN TRAMA			    (Obligatorio)

    TRAMA DE CONFIRMACION DE MENSAJE (RX):

    	>ACK,{IDACK}<

    >		    —> INICIO TRAMA 			(Obligatorio)
    ACK, 	    —> ENCABEZADO			    (Obligatorio)
    {IDACK} 	—> ID DEL ACK			    (Obligatorio)		Numero de identificación de recepción del mensaje recibido exitosamente
    <		    —> FIN TRAMA			    (Obligatorio)


    LOGIN:
    			(TX):       >MDT,ACC,CONTRASEÑA;3
    			(RX):       >RID,1,NOMBRE CONDUCTOR< 		Si fue exitoso el login
    				o
    			(RX):		>RID,0<						Si la contraseña es incorrecta

    ENVIO DE COMANDOS DE BOTONES PARA MENU PRINCIPAL

    Por cada botón se enviara un mensaje con la siguiente estructura:

    	        (TX):   >MDT,RXX;IDACK<
    	        (RX):   >ACK,IDACK<

    Donde el RXX para cada botón es:

    	Operación Nacional: 	R00
    	Viaje Vacío:		    R01
    	Proyectos			    R02
    	Tanqueo			        R03
    	Mantenimiento		    R04

    BOTONES OPERACION NACIONAL:

    	Información de viaje:		(TX): >MDT,R10,TEXTO1,TEXTO2;IDACK<
    							            TEXTO1: Ciudad de destino
    							            TEXTO2: Carga

    	Datos de viaje:			    (TX): >MDT,R11,TEXTO1,TEXTO2;IDACK<
    							            TEXTO1: Numero de manifiesto
    							            TEXTO2: Numero de trailer

    	Pausa Activa:			    (TX): >MDT,R12;IDACK<
    	Pernoctación:			    (TX): >MDT,R13;IDACK<
    	Alimentación:			    (TX): >MDT,R14;IDACK<
    	Otro motivo:			    (TX): >MDT,R15,TEXTO1;IDACK<
    							            TEXTO1: Motivo de la detención

    	Reiniciar Viaje:			(TX): >MDT,R16,IDACK<
    	Llegue a descargar: 		(TX): >MDT,R17;IDACK<
    	Finalización de viaje:		(TX): >MDT,R18,TEXTO1;IDACK<
    							            TEXTO1: Descripción de la finalización

    BOTONES DE VIAJE VACIO:

    	Información de viaje:		(TX): >MDT,R30,TEXTO1,TEXTO2,TEXTO3;IDACK<
    							            TEXTO1: Ciudad destino
    							            TEXTO2: Origen de servicio
    							            TEXTO3: Trailer

    	Detención en ruta:
    	Pausa activa:				(TX): >MDT,R31;IDACK<
    	Pernoctación:				(TX) >MDT,R32;IDACK<
    	Alimentación:				(TX) >MDT,R33;IDACK<
    	Otro motivo: 				(TX) >MDT,R34,TEXTO1;IDACK<
    								        TEXTO1: Motivo de la detención

    	Reiniciar viaje:			(TX) >MDT,R35;IDACK<
    	Llegue a descargar: 		(TX) >MDT,R036;IDACK<
    	Finalización de viaje:		(TX) >MDT,R37,TEXTO1;IDACK<
    								        TEXTO1: Descripción de la finalización

    PROYECTOS:

    	Iniciar turno:				(TX): >MDT,R20;IDACK<
    	Finalizar turno:		    (TX): >MDT,R21;IDACK<

    TANQUEO:

    	Tanqueo:					(TX): >MDT,R40,TEXTO1,TEXTO2;IDACK<
    								        TEXTO1: Nombre de la EDS
    								        TEXTO2: Cantidad de galones

    MANTENIMIENTO:

    	Solicitud de mantenimiento: (TX):   >MDT,R50,TEXTO1;IDACK<
    								        TEXTO1: Solicitud

    	Inicio de mantenimiento:	(TX): >MDT,R51;IDACK<
    	En mantenimiento:			(TX): >MDT,R52,TEXTO1;IDACK<
    	                                    TEXTO: Galones

    	Fin de mantenimiento:	    (TX): >MDT,R53;IDACK<

    CHAT:

    	Envio mensaje:				(TX): >MDT,ACC,Mensaje;IDACK<
    	Recibe mensaje:			    (RX): >CH,Mensaje<


### Dependencies ###


    Favor revise el archivo de dependencias buld.gradle:
    
        * 'com.android.support:appcompat-v7:21.0.0'
        * 'com.android.support:gridlayout-v7:21.0.0'
        * 'com.android.support:support-v4:21.0.0'
        * project(':CoreSerialPort:CoreSerialPortApp')
        
### Developed By ###

* Desarrolladores: 

    - Juan Carlos Ramirez Vargas: Interfaz, logica negocio
    - Edwin Andres Sandoval:    : Liberias de comunicacion
    - Desarrollado en [Widetech](http://widetech.co)

* [Learn Markdown](https://bitbucket.org/tutorials/markdowndemo)