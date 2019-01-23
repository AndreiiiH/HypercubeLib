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
