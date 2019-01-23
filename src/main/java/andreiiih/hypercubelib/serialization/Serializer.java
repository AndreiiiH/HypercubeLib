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
            data[offset++] = values[i];
        }

        return offset;
    }

    public static int writeData(byte[] data, int offset, short value) {
        data[offset++] = (byte)((value >> 8) & 0xff);
        data[offset++] = (byte)((value >> 0) & 0xff);

        return offset;
    }

    public static int writeData(byte[] data, int offset, int value) {
        data[offset++] = (byte)((value >> 24) & 0xff);
        data[offset++] = (byte)((value >> 16) & 0xff);
        data[offset++] = (byte)((value >>  8) & 0xff);
        data[offset++] = (byte)((value >>  0) & 0xff);

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

    public static int writeData(byte[] data, int offset, float value) {
        int intBits = Float.floatToIntBits(value);

        return writeData(data, offset, intBits);
    }

    public static int writeData(byte[] data, int offset, double value) {
        long longBits = Double.doubleToLongBits(value);

        return writeData(data, offset, longBits);
    }

    public static int writeData(byte[] data, int offset, String value) {
        offset = writeData(data, offset, (short)value.length());

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

    public static int readInt(byte[] data, int pointer) {
        return ByteBuffer.wrap(data, pointer, 4).getInt();
    }

    public static long readLong(byte[] data, int pointer) {
        return ByteBuffer.wrap(data, pointer, 8).getLong();
    }

    public static float readFloat(byte[] data, int pointer) {
        return Float.intBitsToFloat(readInt(data, pointer));
    }

    public static double readDouble(byte[] data, int pointer) {
        return Double.longBitsToDouble(readLong(data, pointer));
    }

    public static String readString(byte[] data, int pointer) {
        int length = readShort(data, pointer);

        pointer += 2;

        return new String(readBytes(data, pointer, length));
    }
}
