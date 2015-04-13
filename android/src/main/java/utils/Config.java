package utils;

/**
 * Created by wtjramirez on 3/30/15.
 */
public final class Config {
    public static final String DATABASE_NAME = "mamutSP-db";
    public static final String SEPARATOR = ",";
    public static final String HEADER = "MDT";
    public static final String BASIC_START = HEADER + SEPARATOR;

    public enum valuesOperacionNal {
        TYPE_ACTION_LLEGUE_CARGAR,
        TYPE_ACTION_INICIE_VIAJE,
        TYPE_ACTION_DETENCION_EN_RUTA,
        TYPE_ACTION_LLEGUE_DESCARGAR,
        TYPE_ACTION_FINALIZACION_VIAJE
    }

    public enum valuesViajeVacio {
        TYPE_ACTION_INFORMACION_VIAJE,
        TYPE_ACTION_DETENCION_RUTA,
        TYPE_ACTION_LLEGUE_DESCARGAR,
        TYPE_ACTION_FINALIZACION_VIAJE
    }

    public enum valuesDetencionRuta {
        TYPE_ACTION_PAUSA_ACTIVA,
        TYPE_ACTION_PERNOCTACION,
        TYPE_ACTION_ALIMENTACION,
        TYPE_ACTION_OTRO_MOTIVO,
        TYPE_ACTION_REINICIAR_VIAJE
    }

    public enum valuesProyectos {
        TYPE_ACTION_INICIAR_TURNO,
        TYPE_ACTION_FINALIZAR_TURNO
    }

    public enum valuesTanqueo {
        TYPE_ACTION_TANQUEO
    }

    public enum valuesMantenimiento {
        TYPE_ACTION_SOLICITUD_MANTENIMIENTO,
        TYPE_ACTION_INICIO_MANTENIMIENTO,
        TYPE_ACTION_FIN_MANTENIMIENTO,
        TYPE_ACTION_ENVIAR_GALONES
    }

    public static final class buttonStrings {
        public static final String TYPE_BUTTON_OPERACION_NAL = BASIC_START + "R00";
        public static final String TYPE_BUTTON_VIAJE_VACIO = BASIC_START + "R01";
        public static final String TYPE_BUTTON_PROYECTOS = BASIC_START + "R02";
        public static final String TYPE_BUTTON_TANQUEO = BASIC_START + "R03";
        public static final String TYPE_BUTTON_MANTENIMIENTO = BASIC_START + "R04";
        public static final String TYPE_MESSAGE_LOGIN = BASIC_START + "EID" + SEPARATOR;
        public static final String TYPE_MESSAGE_CHAT = BASIC_START + "ACC" + SEPARATOR;
    }
}
