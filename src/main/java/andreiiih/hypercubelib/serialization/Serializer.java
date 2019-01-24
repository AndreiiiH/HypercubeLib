/**
 * ATTRIBUTION-NONCOMMERCIAL-NODERIVATIVES 4.0 International (CC BY-NC-ND 4.0)
 * https://creativecommons.org/licenses/by-nc-nd/4.0/legalcode
 *
 * You CAN:
 *     Share           Copy and redistribute the material in any medium or format.
 *                     The licensor cannot revoke these freedoms as long as you follow the license terms.
 *
 * You MUST:
 *     Attribution     You must give appropriate credit, provide a link to the license, and indicate if changes were made.
 *                     You may do so in any reasonable manner, but not in any way that suggests the licensor
 *                     endorses you or your use.
 *
 * You CAN'T:
 *     NonCommercial   You may not use the material for commercial purposes.
 *
 *     NoDerivatives   If you remix, transform, or build upon the material, you may not distribute the modified material.
 *
 *     No additional   You may not apply legal terms or technological measures that legally restrict others
 *     restrictions    from doing anything the license permits.
 *
 * Notices:
 *     You do not have to comply with the license for elements of the material in the public domain or where your use
 *     is permitted by an applicable exception or limitation.
 *
 *     No warranties are given. The license may not give you all of the permissions necessary for your intended use.
 *     For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 *
 *
 * Copyright (c) 2018-2019 AndreiiiH <hava.ionut@gmail.com>
 */
package andreiiih.hypercubelib.serialization;

import org.apache.commons.lang3.ArrayUtils;

import java.nio.ByteBuffer;

@SuppressWarnings("PointlessBitwiseExpression")
public final class Serializer {

    public static int writeData(byte[] data, int offset, byte value) {
        data[offset++] = value;

        return offset;
    }

    public static int writeData(byte[] data, int offset, byte[] values) {
        for (int i = 0; i < values.length; i++) {
            offset = writeData(data, offset, values[i]);
        }

        return offset;
    }

    public static int writeData(byte[] data, int offset, short value) {
        data[offset++] = (byte)((value >> 8) & 0xff);
        data[offset++] = (byte)((value >> 0) & 0xff);

        return offset;
    }

    public static int writeData(byte[] data, int offset, short[] values) {
        for (int i = 0; i < values.length; i++) {
            offset = writeData(data, offset, values[i]);
        }

        return offset;
    }

    public static int writeData(byte[] data, int offset, int value) {
        data[offset++] = (byte)((value >> 24) & 0xff);
        data[offset++] = (byte)((value >> 16) & 0xff);
        data[offset++] = (byte)((value >>  8) & 0xff);
        data[offset++] = (byte)((value >>  0) & 0xff);

        return offset;
    }

    public static int writeData(byte[] data, int offset, int[] values) {
        for (int i = 0; i < values.length; i++) {
            offset = writeData(data, offset, values[i]);
        }

        return offset;
    }

    public static int writeData(byte[] data, int offset, long value) {
        data[offset++] = (byte)((value >> 56) & 0xff);
        data[offset++] = (byte)((value >> 48) & 0xff);
        data[offset++] = (byte)((value >> 40) & 0xff);
        data[offset++] = (byte)((value >> 32) & 0xff);
        data[offset++] = (byte)((value >> 24) & 0xff);
        data[offset++] = (byte)((value >> 16) & 0xff);
        data[offset++] = (byte)((value >>  8) & 0xff);
        data[offset++] = (byte)((value >>  0) & 0xff);

        return offset;
    }

    public static int writeData(byte[] data, int offset, long[] values) {
        for (int i = 0; i < values.length; i++) {
            offset = writeData(data, offset, values[i]);
        }

        return offset;
    }

    public static int writeData(byte[] data, int offset, float value) {
        int intBits = Float.floatToIntBits(value);

        return writeData(data, offset, intBits);
    }

    public static int writeData(byte[] data, int offset, float[] values) {
        for (int i = 0; i < values.length; i++) {
            offset = writeData(data, offset, values[i]);
        }

        return offset;
    }

    public static int writeData(byte[] data, int offset, double value) {
        long longBits = Double.doubleToLongBits(value);

        return writeData(data, offset, longBits);
    }

    public static int writeData(byte[] data, int offset, double[] values) {
        for (int i = 0; i < values.length; i++) {
            offset = writeData(data, offset, values[i]);
        }

        return offset;
    }

    public static int writeData(byte[] data, int offset, String value) {
        return writeData(data, offset, value.getBytes());
    }

    public static byte readByte(byte[] data, int pointer) {
        return data[pointer];
    }

    public static byte[] readBytes(byte[] data, int pointer, int length) {
        return ArrayUtils.subarray(data, pointer, pointer + length);
    }

    public static short readShort(byte[] data, int pointer) {
        return ByteBuffer.wrap(data, pointer, 2).getShort();
    }

    public static short[] readShorts(byte[] data, int pointer, int length) {
        short[] out = new short[length];

        for (int i = 0; i < length; i++) {
            out[i] = readShort(data, pointer + (i * 2));
        }

        return out;
    }

    public static int readInt(byte[] data, int pointer) {
        return ByteBuffer.wrap(data, pointer, 4).getInt();
    }

    public static int[] readInts(byte[] data, int pointer, int length) {
        int[] out = new int[length];

        for (int i = 0; i < length; i++) {
            out[i] = readInt(data, pointer + (i * 4));
        }

        return out;
    }

    public static long readLong(byte[] data, int pointer) {
        return ByteBuffer.wrap(data, pointer, 8).getLong();
    }

    public static long[] readLongs(byte[] data, int pointer, int length) {
        long[] out = new long[length];

        for (int i = 0; i < length; i++) {
            out[i] = readLong(data, pointer + (i * 8));
        }

        return out;
    }

    public static float readFloat(byte[] data, int pointer) {
        return Float.intBitsToFloat(readInt(data, pointer));
    }

    public static float[] readFloats(byte[] data, int pointer, int length) {
        float[] out = new float[length];

        for (int i = 0; i < length; i++) {
            out[i] = readFloat(data, pointer + (i * 4));
        }

        return out;
    }

    public static double readDouble(byte[] data, int pointer) {
        return Double.longBitsToDouble(readLong(data, pointer));
    }

    public static double[] readDoubles(byte[] data, int pointer, int length) {
        double[] out = new double[length];

        for (int i = 0; i < length; i++) {
            out[i] = readDouble(data, pointer + (i * 8));
        }

        return out;
    }

    public static String readString(byte[] data, int pointer, int length) {
        return new String(readBytes(data, pointer, length));
    }
}
