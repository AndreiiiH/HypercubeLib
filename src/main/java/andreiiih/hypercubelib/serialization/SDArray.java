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

import andreiiih.hypercubelib.core.IntRef;
import andreiiih.hypercubelib.exceptions.ArgumentTooLargeException;
import andreiiih.hypercubelib.exceptions.UnsupportedContainerTypeException;
import andreiiih.hypercubelib.exceptions.UnsupportedDataTypeException;

public class SDArray extends SDField {

    private SDArray(String name, byte dataType) throws ArgumentTooLargeException {
        super(ContainerType.ARRAY, name, dataType);
    }

    @Override
    public void copyFromBuffer(byte[] src, IntRef pointer) throws
            UnsupportedContainerTypeException, UnsupportedDataTypeException {
        containerType = Serializer.readByte(src, pointer.g());
        if (containerType != ContainerType.ARRAY) {
            throw new UnsupportedContainerTypeException(ContainerType.ARRAY, containerType);
        }

        super.copyFromBuffer(src, pointer);
    }

    @Override
    public Object deserialise() {
        switch (dataType) {
            case DataType.BYTE:
                return Serializer.readBytes(data, 0, dataLength / DataType.getDataTypeSize(DataType.BYTE));
            case DataType.SHORT:
                return Serializer.readShorts(data, 0, dataLength / DataType.getDataTypeSize(DataType.SHORT));
            case DataType.INT:
                return Serializer.readInts(data, 0, dataLength / DataType.getDataTypeSize(DataType.INT));
            case DataType.LONG:
                return Serializer.readLongs(data, 0, dataLength / DataType.getDataTypeSize(DataType.LONG));
            case DataType.FLOAT:
                return Serializer.readFloats(data, 0, dataLength / DataType.getDataTypeSize(DataType.FLOAT));
            case DataType.DOUBLE:
                return Serializer.readDoubles(data, 0, dataLength / DataType.getDataTypeSize(DataType.DOUBLE));
        }

        return null;
    }

    public static class Builder {

        public static SDArray Byte(String name, byte[] values) throws ArgumentTooLargeException {
            SDArray array = new SDArray(name, DataType.BYTE);

            array.dataLength = DataType.getDataTypeSize(DataType.BYTE) * values.length;
            array.data = new byte[array.dataLength];
            Serializer.writeData(array.data, 0, values);

            return array;
        }

        public static SDArray Short(String name, short[] values) throws ArgumentTooLargeException {
            SDArray array = new SDArray(name, DataType.SHORT);

            array.dataLength = DataType.getDataTypeSize(DataType.SHORT) * values.length;
            array.data = new byte[array.dataLength];
            Serializer.writeData(array.data, 0, values);

            return array;
        }

        public static SDArray Int(String name, int[] values) throws ArgumentTooLargeException {
            SDArray array = new SDArray(name, DataType.INT);

            array.dataLength = DataType.getDataTypeSize(DataType.INT) * values.length;
            array.data = new byte[array.dataLength];
            Serializer.writeData(array.data, 0, values);

            return array;
        }

        public static SDArray Long(String name, long[] values) throws ArgumentTooLargeException {
            SDArray array = new SDArray(name, DataType.LONG);

            array.dataLength = DataType.getDataTypeSize(DataType.LONG) * values.length;
            array.data = new byte[array.dataLength];
            Serializer.writeData(array.data, 0, values);

            return array;
        }

        public static SDArray Float(String name, float[] values) throws ArgumentTooLargeException {
            SDArray array = new SDArray(name, DataType.FLOAT);

            array.dataLength = DataType.getDataTypeSize(DataType.FLOAT) * values.length;
            array.data = new byte[array.dataLength];
            Serializer.writeData(array.data, 0, values);

            return array;
        }

        public static SDArray Double(String name, double[] values) throws ArgumentTooLargeException {
            SDArray array = new SDArray(name, DataType.DOUBLE);

            array.dataLength = DataType.getDataTypeSize(DataType.DOUBLE) * values.length;
            array.data = new byte[array.dataLength];
            Serializer.writeData(array.data, 0, values);

            return array;
        }

        public static <T extends Number> SDArray Generic(String name, T[] values, Class<T> genericType) throws ArgumentTooLargeException {
            if (genericType.equals(Byte.TYPE)) {
                byte[] parsed = new byte[values.length];
                for(int i = 0; i < values.length; i++) {
                    parsed[i] = Byte.parseByte(values[i].toString());
                }

                return Byte(name, parsed);
            } else if (genericType.equals(Short.TYPE)) {
                short[] parsed = new short[values.length];
                for(int i = 0; i < values.length; i++) {
                    parsed[i] = Short.parseShort(values[i].toString());
                }

                return Short(name, parsed);
            } else if (genericType.equals(Integer.TYPE)) {
                int[] parsed = new int[values.length];
                for(int i = 0; i < values.length; i++) {
                    parsed[i] = Integer.parseInt(values[i].toString());
                }

                return Int(name, parsed);
            } else if (genericType.equals(Long.TYPE)) {
                long[] parsed = new long[values.length];
                for(int i = 0; i < values.length; i++) {
                    parsed[i] = Long.parseLong(values[i].toString());
                }

                return Long(name, parsed);
            } else if (genericType.equals(Float.TYPE)) {
                float[] parsed = new float[values.length];
                for(int i = 0; i < values.length; i++) {
                    parsed[i] = Float.parseFloat(values[i].toString());
                }

                return Float(name, parsed);
            } else if (genericType.equals(Double.TYPE)) {
                double[] parsed = new double[values.length];
                for(int i = 0; i < values.length; i++) {
                    parsed[i] = Double.parseDouble(values[i].toString());
                }

                return Double(name, parsed);
            }

            return null;
        }

        public static SDArray Buffer(byte[] buffer, IntRef pointer) throws
                ArgumentTooLargeException, UnsupportedContainerTypeException, UnsupportedDataTypeException {
            SDArray array = Byte("generic", new byte[0]);

            array.copyFromBuffer(buffer, pointer);

            return array;
        }
    }
}
