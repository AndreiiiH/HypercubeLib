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

public abstract class SDObject<T extends SDObject> extends SDPart<T> {

    protected long dataLength;
    protected byte[] data;

    private SDField sdDataLength;
    private SDArray sdData;

    public SDObject(Class<T> type) throws IdentifierNotFoundException {
        super(type);

        init();
    }

    @Override
    protected void init() {
        super.init();

        data = new byte[] { 0 };

        try {
            sdDataLength = SDField.Builder.Long("dataLength", dataLength);
            sdData = SDArray.Builder.Byte("data", data);
        } catch (Exception e) {
            HypercubeLib.LOG.error("Error encountered during header init", e);
        }
    }

    protected abstract void serialize();

    protected abstract T deserialise();

    @Override
    public void copyToBuffer(byte[] dest, IntRef pointer) {
        super.copyToBuffer(dest, pointer);

        serialize();

        sdDataLength.copyToBuffer(dest, pointer);
        sdData.copyToBuffer(dest, pointer);
    }

    @Override
    public SDObject<T> copyFromBuffer(byte[] src, IntRef pointer) {
        super.copyFromBuffer(src, pointer);

        try {
            sdDataLength.copyFromBuffer(src, pointer);
            sdData.copyFromBuffer(src, pointer);
        } catch (Exception e) {
            HypercubeLib.LOG.error(e);
        }

        dataLength = (long)sdDataLength.deserialise();
        data = (byte[])sdData.deserialise();

        return this;
    }

    @Override
    public int size() {
        return
                super.size() +
                sdDataLength.size() +
                sdData.size();
    }
}