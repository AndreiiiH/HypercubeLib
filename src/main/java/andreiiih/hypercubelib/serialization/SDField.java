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
import andreiiih.hypercubelib.core.Ref;
import andreiiih.hypercubelib.exceptions.ArgumentTooLargeException;
import andreiiih.hypercubelib.exceptions.UnsupportedContainerTypeException;
import andreiiih.hypercubelib.exceptions.UnsupportedDataTypeException;

public class SDField {

    protected byte containerType;
    protected short nameLength;
    protected String name;
    protected byte dataType;
    protected int dataLength;
    protected byte[] data;

    protected SDField(byte containerType, String name, byte dataType) throws ArgumentTooLargeException {
        if (name.length() > Short.MAX_VALUE) {
            throw new ArgumentTooLargeException("containerName", Short.MAX_VALUE, name.length());
        }

        this.containerType = containerType;
        this.nameLength = (short)name.length();
        this.name = name;
        this.dataType = dataType;
    }

    public void copyToBuffer(byte[] dest, IntRef pointer) throws ArrayIndexOutOfBoundsException {
        if (dest.length < size() + pointer.g()) {
            throw new ArrayIndexOutOfBoundsException(String.format("The destination array is too small! Expected min %d, got %d",
                    size() + pointer.g(), dest.length));
        }

        pointer.s(Serializer.writeData(dest, pointer.g(), containerType));
        pointer.s(Serializer.writeData(dest, pointer.g(), nameLength));
        pointer.s(Serializer.writeData(dest, pointer.g(), name));
        pointer.s(Serializer.writeData(dest, pointer.g(), dataType));
        pointer.s(Serializer.writeData(dest, pointer.g(), dataLength));

        for (int i = 0; i < data.length; i++) {
            pointer.s(Serializer.writeData(dest, pointer.g(), data[i]));
        }
    }

    public void copyFromBuffer(byte[] src, IntRef pointer) throws
            UnsupportedContainerTypeException, UnsupportedDataTypeException {
        containerType = Serializer.readByte(src, pointer.g());
        pointer.incr();
        if (containerType != ContainerType.FIELD && containerType != ContainerType.ARRAY) {
            throw new UnsupportedContainerTypeException(ContainerType.FIELD, containerType);
        }

        nameLength = Serializer.readShort(src, pointer.g());
        pointer.add(DataType.getDataTypeSize(DataType.SHORT));
        name = Serializer.readString(src, pointer.g(), nameLength);
        pointer.add(nameLength);

        dataType = Serializer.readByte(src, pointer.g());
        pointer.incr();
        if (dataType < 1 || dataType > 7) {
            throw new UnsupportedDataTypeException(dataType);
        }

        dataLength = Serializer.readInt(src, pointer.g());
        pointer.add(DataType.getDataTypeSize(DataType.INT));

        data = Serializer.readBytes(src, pointer.g(), dataLength);

        pointer.add(dataLength);
    }

    public void serialize(Object value) {
        switch (dataType) {
            case DataType.BYTE:
                Serializer.writeData(data, 0, (byte) value);
                return;
            case DataType.SHORT:
                Serializer.writeData(data, 0, (short) value);
                return;
            case DataType.INT:
                Serializer.writeData(data, 0, (int) value);
                return;
            case DataType.LONG:
                Serializer.writeData(data, 0, (long) value);
                return;
            case DataType.FLOAT:
                Serializer.writeData(data, 0, (float) value);
                return;
            case DataType.DOUBLE:
                Serializer.writeData(data, 0, (double) value);
                return;
            case DataType.STRING:
                Serializer.writeData(data, 0, value.toString());
                return;
        }
    }

    public Object deserialise() {
        switch (dataType) {
            case DataType.BYTE:
                return Serializer.readByte(data, 0);
            case DataType.SHORT:
                return Serializer.readShort(data, 0);
            case DataType.INT:
                return Serializer.readInt(data, 0);
            case DataType.LONG:
                return Serializer.readLong(data, 0);
            case DataType.FLOAT:
                return Serializer.readFloat(data, 0);
            case DataType.DOUBLE:
                return Serializer.readDouble(data, 0);
            case DataType.STRING:
                return Serializer.readString(data, 0, dataLength);
        }

        return null;
    }

    public int size() {
        return
            DataType.getDataTypeSize(DataType.BYTE) +
            DataType.getDataTypeSize(DataType.SHORT) +
            nameLength +
            DataType.getDataTypeSize(DataType.BYTE) +
            DataType.getDataTypeSize(DataType.INT) +
            data.length;
    }

    public static class Builder {

        public static SDField Byte(String name, byte value) throws ArgumentTooLargeException {
            SDField field = new SDField(ContainerType.FIELD, name, DataType.BYTE);

            field.dataLength = DataType.getDataTypeSize(DataType.BYTE);
            field.data = new byte[field.dataLength];
            Serializer.writeData(field.data, 0, value);

            return field;
        }

        public static SDField Short(String name, short value) throws ArgumentTooLargeException {
            SDField field = new SDField(ContainerType.FIELD, name, DataType.SHORT);

            field.dataLength = DataType.getDataTypeSize(DataType.SHORT);
            field.data = new byte[field.dataLength];
            Serializer.writeData(field.data, 0, value);

            return field;
        }

        public static SDField Int(String name, int value) throws ArgumentTooLargeException {
            SDField field = new SDField(ContainerType.FIELD, name, DataType.INT);

            field.dataLength = DataType.getDataTypeSize(DataType.INT);
            field.data = new byte[field.dataLength];
            Serializer.writeData(field.data, 0, value);

            return field;
        }

        public static SDField Long(String name, long value) throws ArgumentTooLargeException {
            SDField field = new SDField(ContainerType.FIELD, name, DataType.LONG);

            field.dataLength = DataType.getDataTypeSize(DataType.LONG);
            field.data = new byte[field.dataLength];
            Serializer.writeData(field.data, 0, value);

            return field;
        }

        public static SDField Float(String name, float value) throws ArgumentTooLargeException {
            SDField field = new SDField(ContainerType.FIELD, name, DataType.FLOAT);

            field.dataLength = DataType.getDataTypeSize(DataType.FLOAT);
            field.data = new byte[field.dataLength];
            Serializer.writeData(field.data, 0, value);

            return field;
        }

        public static SDField Double(String name, double value) throws ArgumentTooLargeException {
            SDField field = new SDField(ContainerType.FIELD, name, DataType.DOUBLE);

            field.dataLength = DataType.getDataTypeSize(DataType.DOUBLE);
            field.data = new byte[field.dataLength];
            Serializer.writeData(field.data, 0, value);

            return field;
        }

        public static SDField String(String name, String value) throws ArgumentTooLargeException {
            if (value.length() > Integer.MAX_VALUE) {
                throw new ArgumentTooLargeException("value", Integer.MAX_VALUE, value.length());
            }

            SDField field = new SDField(ContainerType.FIELD, name, DataType.STRING);

            field.dataLength = (short)value.length();
            field.data = new byte[(short)value.length()];
            Serializer.writeData(field.data, 0, value);

            return field;
        }

        public static SDField Buffer(byte[] buffer, IntRef pointer) throws
                ArgumentTooLargeException, UnsupportedContainerTypeException, UnsupportedDataTypeException {
            SDField field = Byte("generic", (byte)0);

            field.copyFromBuffer(buffer, pointer);

            return field;
        }
    }
}
