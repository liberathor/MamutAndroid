package co.com.widetech.mamut.android.utils;

/**
 * Created by wtjramirez on 3/30/15.
 */
public final class Config {
    public static final String DATABASE_NAME = "mamutSP-db";
    public static final String SEPARATOR = ",";
    public static final String HEADER = "MDT";
    public static final String BASIC_START = HEADER + SEPARATOR;

    public static final class valuesOperacionNal {
        public static final String TYPE_ACTION_LLEGUE_CARGAR = "R0";
        public static final String TYPE_ACTION_INICIE_VIAJE = "R10";
        public static final String TYPE_ACTION_DETENCION_EN_RUTA = "R2";
        public static final String TYPE_ACTION_LLEGUE_DESCARGAR = "R17";
        public static final String TYPE_ACTION_FINALIZACION_VIAJE = "R18";
    }

    public static final class valuesViajeVacio {
        public static final String TYPE_ACTION_INFORMACION_VIAJE = "R1";
        public static final String TYPE_ACTION_DETENCION_EN_RUTA = "R31";
        public static final String TYPE_ACTION_LLEGUE_DESCARGAR = "R36";
        public static final String TYPE_ACTION_FINALIZACION_VIAJE = "R37";
        public static final String TYPE_ACTION_INFORMACION_VIAJE_VACIO = "R30";
    }

    public static final class valuesDetencionRuta {
        public static final String TYPE_ACTION_PAUSA_ACTIVA = "R12";
        public static final String TYPE_ACTION_PERNOCTACION = "R13";
        public static final String TYPE_ACTION_ALIMENTACION = "R14";
        public static final String TYPE_ACTION_OTRO_MOTIVO = "R15";
        public static final String TYPE_ACTION_REINICIAR_VIAJE = "R16";
    }

    public static final class valuesDetencionRutaViajeVacio {
        public static final String TYPE_ACTION_PAUSA_ACTIVA = "R31";
        public static final String TYPE_ACTION_PERNOCTACION = "R32";
        public static final String TYPE_ACTION_ALIMENTACION = "R33";
        public static final String TYPE_ACTION_OTRO_MOTIVO = "R34";
        public static final String TYPE_ACTION_REINICIAR_VIAJE = "R35";
    }

    public static final class valuesDetencionRutaOperacionNal {
        public static final String TYPE_ACTION_PAUSA_ACTIVA = "R18";
        public static final String TYPE_ACTION_PERNOCTACION = "R19";
        public static final String TYPE_ACTION_ALIMENTACION = "R20";
        public static final String TYPE_ACTION_OTRO_MOTIVO = "R21";
        public static final String TYPE_ACTION_REINICIAR_VIAJE = "R22";
    }

    public static final class valuesProyectos {
        public static final String TYPE_ACTION_INICIAR_TURNO = "R20";
        public static final String TYPE_ACTION_FINALIZAR_TURNO = "R21";
    }

    public static final class valuesTanqueo {
        public static final String TYPE_ACTION_TANQUEO = "R40";
    }

    public static final class valuesMantenimiento {
        public static final String TYPE_ACTION_SOLICITUD_MANTENIMIENTO = "R50";
        public static final String TYPE_ACTION_INICIO_MANTENIMIENTO = "R51";
        public static final String TYPE_ACTION_FIN_MANTENIMIENTO = "R53";
        public static final String TYPE_ACTION_ENVIAR_GALONES = "R52";
    }

    public static final class buttonStrings {
        public static final String TYPE_BUTTON_OPERACION_NAL = BASIC_START + "R00";
        public static final String TYPE_BUTTON_PROYECTOS = BASIC_START + "R01";
        public static final String TYPE_BUTTON_VIAJE_VACIO = BASIC_START + "R02";
        public static final String TYPE_BUTTON_TANQUEO = BASIC_START + "R03";
        public static final String TYPE_BUTTON_MANTENIMIENTO = BASIC_START + "R04";
        public static final String TYPE_MESSAGE_LOGIN = BASIC_START + "EID" + SEPARATOR;
        public static final String TYPE_MESSAGE_CHAT = BASIC_START + "ACC" + SEPARATOR;
    }
}
