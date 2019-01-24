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

import andreiiih.hypercubelib.HypercubeLib;
import andreiiih.hypercubelib.core.IntRef;
import andreiiih.hypercubelib.exceptions.CorruptedDataException;
import andreiiih.hypercubelib.exceptions.IdentifierNotFoundException;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public abstract class SDPart<T extends SDPart> {

    protected Class<? extends SDPart> type;
    protected byte id;

    protected SDField sdId;

    public SDPart(Class<T> type) throws IdentifierNotFoundException {
        this.type = type;
        this.id = ObjectTypeRegistry.get(type);
    }

    protected void init() {
        try {
            this.sdId = SDField.Builder.Byte("id", id);
        } catch (Exception e) {
            HypercubeLib.LOG.error("Error encountered during header init", e);
        }
    }

    public void copyToBuffer(byte[] dest, IntRef pointer) {
        sdId.copyToBuffer(dest, pointer);
    }

    public void reflectiveCopyToBuffer(byte[] dest, IntRef pointer) {
        List<SDField> sdFields = new LinkedList<>();
        List<SDArray> sdArrays = new LinkedList<>();

        Field[] fields = SDHeader.class.getFields();
        try {
            for (Field field : fields) {
                if (field.getType().isArray()) {
                    if (field.getType().getComponentType().equals(Byte.TYPE)) {
                        sdArrays.add(SDArray.Builder.Generic(field.getName(), this.<Byte>unpackArray(field.get(this)), Byte.TYPE));
                    } else if (field.getType().getComponentType().equals(Short.TYPE)) {
                        sdArrays.add(SDArray.Builder.Generic(field.getName(), this.<Short>unpackArray(field.get(this)), Short.TYPE));
                    } else if (field.getType().getComponentType().equals(Integer.TYPE)) {
                        sdArrays.add(SDArray.Builder.Generic(field.getName(), this.<Integer>unpackArray(field.get(this)), Integer.TYPE));
                    } else if (field.getType().getComponentType().equals(Long.TYPE)) {
                        sdArrays.add(SDArray.Builder.Generic(field.getName(), this.<Long>unpackArray(field.get(this)), Long.TYPE));
                    } else if (field.getType().getComponentType().equals(Float.TYPE)) {
                        sdArrays.add(SDArray.Builder.Generic(field.getName(), this.<Float>unpackArray(field.get(this)), Float.TYPE));
                    } else if (field.getType().getComponentType().equals(Double.TYPE)) {
                        sdArrays.add(SDArray.Builder.Generic(field.getName(), this.<Double>unpackArray(field.get(this)), Double.TYPE));
                    }
                } else {
                    if (field.getType().equals(Byte.TYPE)) {
                        sdFields.add(SDField.Builder.Byte(field.getName(), field.getByte(this)));
                    } else if (field.getType().equals(Short.TYPE)) {
                        sdFields.add(SDField.Builder.Short(field.getName(), field.getShort(this)));
                    } else if (field.getType().equals(Integer.TYPE)) {
                        sdFields.add(SDField.Builder.Int(field.getName(), field.getInt(this)));
                    } else if (field.getType().equals(Long.TYPE)) {
                        sdFields.add(SDField.Builder.Long(field.getName(), field.getLong(this)));
                    } else if (field.getType().equals(Float.TYPE)) {
                        sdFields.add(SDField.Builder.Float(field.getName(), field.getFloat(this)));
                    } else if (field.getType().equals(Double.TYPE)) {
                        sdFields.add(SDField.Builder.Double(field.getName(), field.getDouble(this)));
                    } else if (field.getType().equals(String.class)) {
                        sdFields.add(SDField.Builder.String(field.getName(), field.get(this).toString()));
                    }
                }
            }
        } catch (Exception e) {
            HypercubeLib.LOG.error("Error encountered during reflective copy to buffer!", e);
        }

        for (SDField field : sdFields) {
            field.copyToBuffer(dest, pointer);
        }

        for (SDArray array : sdArrays) {
            array.copyToBuffer(dest, pointer);
        }
    }

    public SDPart copyFromBuffer(byte[] src, IntRef pointer) {
        try {
            sdId.copyFromBuffer(src, pointer);
        } catch (Exception e) {
            HypercubeLib.LOG.error("Error encountered during copy from buffer!", e);
        }

        id = (byte) sdId.deserialise();

        try {
            type = ObjectTypeRegistry.get(id);
        } catch (Exception e) {
            HypercubeLib.LOG.error("Error encountered during copy from buffer!", e);
        }

        return this;
    }

    public SDPart reflectiveCopyFromBuffer(byte[] src, IntRef pointer) {
        try {
            SDPart instance = type.newInstance();

            Field[] fields = type.getFields();
            for (Field field : fields) {
                if (field.getType().isArray()) {
                    SDArray array = SDArray.Builder.Buffer(src, pointer);

                    if (!array.name.equals(field.getName())) {
                        throw new CorruptedDataException(String.format("Name mismatch between %s and %s", array.name, field.getName()));
                    }

                    field.set(instance, array.deserialise());
                } else {
                    SDField sdField = SDField.Builder.Buffer(src, pointer);

                    if (!sdField.name.equals(field.getName())) {
                        throw new CorruptedDataException(String.format("Name mismatch between %s and %s", sdField.name, field.getName()));
                    }

                    field.set(instance, sdField.deserialise());
                }
            }

            return instance;
        } catch (Exception e) {
            HypercubeLib.LOG.error("Error encountered during reflective copy from buffer!", e);
        }

        return null;
    }

    public int size() {
        return sdId.size();
    }

    protected <U extends Number> U[] unpackArray(Object array) {
        Object[] out = new Object[Array.getLength(array)];

        for (int i = 0; i < out.length; i++) {
            out[i] = Array.get(array, i);
        }

        return (U[]) out;
    }

    public static SDPart createFromBuffer(byte[] src, IntRef pointer) {
        try {
            SDField sdId = SDField.Builder.Buffer(src, pointer);
            byte id = (byte) sdId.deserialise();

            Class<? extends SDPart> type = ObjectTypeRegistry.get(id);
            SDPart instance = type.newInstance();

            instance.copyFromBuffer(src, pointer);

            return instance;
        } catch (Exception e) {
            HypercubeLib.LOG.error("Error encountered while attempting to create from buffer!", e);
        }

        return null;
    }
}