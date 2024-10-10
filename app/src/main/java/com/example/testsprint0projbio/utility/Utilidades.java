package com.example.testsprint0projbio.utility;


import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Clase Utilidades que contiene métodos estáticos para la manipulación de datos.
 */
public class Utilidades {

    /**
     * Convierte un String en un array de bytes.
     * @param texto El String a convertir.
     * @return El array de bytes correspondiente al String.
     */
    public static byte[] stringToBytes ( String texto ) {
        return texto.getBytes();
        // byte[] b = string.getBytes(StandardCharsets.UTF_8); // Ja
    } // ()

    /**
     * Convierte un String en un UUID.
     * @param uuid El String a convertir.
     * @return El UUID correspondiente al String.
     * @throws Error si el String no tiene 16 caracteres.
     */
    public static UUID stringToUUID(String uuid ) {
        if ( uuid.length() != 16 ) {
            throw new Error( "stringUUID: string no tiene 16 caracteres ");
        }
        byte[] comoBytes = uuid.getBytes();

        String masSignificativo = uuid.substring(0, 8);
        String menosSignificativo = uuid.substring(8, 16);
        UUID res = new UUID( Utilidades.bytesToLong( masSignificativo.getBytes() ), Utilidades.bytesToLong( menosSignificativo.getBytes() ) );

        // Log.d( MainActivity.ETIQUETA_LOG, " \n\n*** stringToUUID * " + uuid  + "=?=" + Utilidades.uuidToString( res ) );

        // UUID res = UUID.nameUUIDFromBytes( comoBytes ); no va como quiero

        return res;
    } // ()

    /**
     * Convierte un UUID en un String.
     * @param uuid El UUID a convertir.
     * @return El String correspondiente al UUID.
     */
    public static String uuidToString ( UUID uuid ) {
        return bytesToString( dosLongToBytes( uuid.getMostSignificantBits(), uuid.getLeastSignificantBits() ) );
    } // ()

    /**
     * Convierte un UUID en un String en formato hexadecimal.
     * @param uuid El UUID a convertir.
     * @return El String en formato hexadecimal correspondiente al UUID.
     */
    public static String uuidToHexString ( UUID uuid ) {
        return bytesToHexString( dosLongToBytes( uuid.getMostSignificantBits(), uuid.getLeastSignificantBits() ) );
    } // ()

    /**
     * Convierte un array de bytes en un String.
     * @param bytes El array de bytes a convertir.
     * @return El String correspondiente al array de bytes.
     */
    public static String bytesToString( byte[] bytes ) {
        if (bytes == null ) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append( (char) b );
        }
        return sb.toString();
    }

    /**
     * Convierte dos longs en un array de bytes.
     * @param masSignificativos El primer long a convertir.
     * @param menosSignificativos El segundo long a convertir.
     * @return El array de bytes correspondiente a los dos longs.
     */
    public static byte[] dosLongToBytes( long masSignificativos, long menosSignificativos ) {
        ByteBuffer buffer = ByteBuffer.allocate( 2 * Long.BYTES );
        buffer.putLong( masSignificativos );
        buffer.putLong( menosSignificativos );
        return buffer.array();
    }

    /**
     * Convierte un array de bytes en un int.
     * @param bytes El array de bytes a convertir.
     * @return El int correspondiente al array de bytes.
     */
    public static int bytesToInt( byte[] bytes ) {

        return new BigInteger(bytes).intValue();
    }

    /**
     * Convierte un array de bytes en un long.
     * @param bytes El array de bytes a convertir.
     * @return El long correspondiente al array de bytes.
     */
    public static long bytesToLong( byte[] bytes ) {

        return new BigInteger(bytes).longValue();
    }

    /**
     * Convierte un array de bytes en un int.
     * @param bytes El array de bytes a convertir.
     * @return El int correspondiente al array de bytes.
     * @throws Error si el array de bytes tiene más de 4 bytes.
     */
    public static int bytesToIntOK( byte[] bytes ) {
        if (bytes == null ) {
            return 0;
        }

        if ( bytes.length > 4 ) {
            throw new Error( "demasiados bytes para pasar a int ");
        }
        int res = 0;



        for( byte b : bytes ) {
           /*
           Log.d( MainActivity.ETIQUETA_LOG, "bytesToInt(): byte: hex=" + Integer.toHexString( b )
                   + " dec=" + b + " bin=" + Integer.toBinaryString( b ) +
                   " hex=" + Byte.toString( b )
           );
           */
            res =  (res << 8) // * 16
                    + (b & 0xFF); // para quedarse con 1 byte (2 cuartetos) de lo que haya en b
        } // for

        if ( (bytes[ 0 ] & 0x8) != 0 ) {
            // si tiene signo negativo (un 1 a la izquierda del primer byte
            res = -((byte)res)-1; // complemento a 2 () de res pero como byte, -1
        }
       /*
        Log.d( MainActivity.ETIQUETA_LOG, "bytesToInt(): res = " + res + " ~res=" + (res ^ 0xffff)
                + "~res=" + ~((byte) res)
        );
        */

        return res;
    } // ()

    /**
     * @brief Convierte un array de bytes en un número entero sin signo.
     *
     * Esta función convierte un array de dos bytes en un número entero sin signo
     * de 16 bits. Si el array no contiene exactamente dos bytes, devuelve 1.
     *
     * @param bytes Array de bytes que contiene los datos a convertir.
     *              Debe tener una longitud de exactamente 2.
     *
     * @return El valor entero sin signo correspondiente a los dos bytes
     *         proporcionados. Si el array no tiene longitud 2, devuelve 1.
     */
    public static int majorToUnisgnedInt( byte[] bytes ) {
        if (bytes.length != 2 ) {
            return 1;
        }
        int result = ((bytes[0] & 0xFF) << 8) | (bytes[1] & 0xFF);
        return result;
    }
    /**
     * Convierte un array de bytes en un String en formato hexadecimal.
     * @param bytes El array de bytes a convertir.
     * @return El String en formato hexadecimal correspondiente al array de bytes.
     */
    public static String bytesToHexString( byte[] bytes ) {

        if (bytes == null ) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
            sb.append(':');
        }
        return sb.toString();
    } // ()

} // class
// -----------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------