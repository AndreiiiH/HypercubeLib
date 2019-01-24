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
import andreiiih.hypercubelib.exceptions.IdentifierNotFoundException;
import andreiiih.hypercubelib.exceptions.UnsupportedContainerTypeException;

import java.util.LinkedList;

public class SDBody extends SDPart<SDBody> {

    protected LinkedList<SDObject> objects = new LinkedList<>();

    public SDBody() throws IdentifierNotFoundException {
        super(SDBody.class);

        init();
    }

    public void addObject(SDObject object) {
        objects.add(object);
    }

    public void removeObject(SDObject object) {
        objects.remove(object);
    }

    public LinkedList<SDObject> getObjects() {
        return objects;
    }

    @Override
    public void copyToBuffer(byte[] dest, IntRef pointer) {
        super.copyToBuffer(dest, pointer);

        for (SDObject object : objects) {
            object.copyToBuffer(dest, pointer);
        }
    }

    @Override
    public SDBody copyFromBuffer(byte[] src, IntRef pointer) {
        super.copyFromBuffer(src, pointer);

        try {
            if (ObjectTypeRegistry.get(src[pointer.g()]) != SDBody.class) {
                HypercubeLib.LOG.error("Error encountered during body copy from buffer",
                        new UnsupportedContainerTypeException(ObjectTypeRegistry.get(SDBody.class), src[pointer.g()]));
                return null;
            }
        } catch (Exception e) {
            HypercubeLib.LOG.error("Error encountered during body copy from buffer", e);
            return null;
        }

        pointer.incr();

        while (pointer.g() < src.length) {
            try {
                Class<?> type = ObjectTypeRegistry.get(src[pointer.g()]);
                if (type.getSuperclass() != SDObject.class) {
                    HypercubeLib.LOG.error("Error encountered during body copy from buffer",
                            new UnsupportedContainerTypeException((byte)0, src[pointer.g()]));
                    return null;
                }

                SDObject instance = (SDObject)type.newInstance();
                instance.copyFromBuffer(src, pointer);
                objects.add(instance);
            } catch (Exception e) {
                HypercubeLib.LOG.error("Error encountered during body copy from buffer", e);
                return null;
            }
        }

        return this;
    }

    @Override
    public int size() {
        int size = super.size();

        for (SDObject object : objects) {
            size += object.size();
        }

        return size;
    }
}
