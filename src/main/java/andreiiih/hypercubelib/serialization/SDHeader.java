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
import andreiiih.hypercubelib.HypercubeLibConstants;
import andreiiih.hypercubelib.core.IntRef;
import andreiiih.hypercubelib.exceptions.IdentifierNotFoundException;

public class SDHeader extends SDObject<SDHeader> {

    protected String fileIdentifier = new String(HypercubeLibConstants.CACHE_FILE_ID);
    protected byte fileType;
    protected short fileVersion;
    protected String modId = HypercubeLibConstants.getModId();
    protected long dataLength;

    private SDField sdFileIdentifier;
    private SDField sdFileType;
    private SDField sdFileVersion;
    private SDField sdModId;
    private SDField sdDataLength;

    public SDHeader() throws IdentifierNotFoundException {
        this(HypercubeLibConstants.TYPE_MODEL, HypercubeLibConstants.MODEL_VERSION);
    }

    public SDHeader(byte fileType, short fileVersion) throws IdentifierNotFoundException {
        super(SDHeader.class);
        this.fileType = fileType;
        this.fileVersion = fileVersion;

        init();
    }

    @Override
    protected void init() {
        super.init();

        try {
            sdFileIdentifier = SDField.Builder.String("fileIdentifier", fileIdentifier);
            sdFileType = SDField.Builder.Byte("fileType", fileType);
            sdFileVersion = SDField.Builder.Short("fileVersion", fileVersion);
            sdModId = SDField.Builder.String("modId", modId);
            sdDataLength = SDField.Builder.Long("dataLength", dataLength);
        } catch (Exception e) {
            HypercubeLib.LOG.error("Error encountered during header init", e);
        }
    }

    @Override
    public void copyToBuffer(byte[] dest, IntRef pointer) {
        super.copyToBuffer(dest, pointer);

        sdFileIdentifier.copyToBuffer(dest, pointer);
        sdFileType.copyToBuffer(dest, pointer);
        sdFileVersion.copyToBuffer(dest, pointer);
        sdModId.copyToBuffer(dest, pointer);
        sdDataLength.copyToBuffer(dest, pointer);
    }

    @Override
    public SDHeader copyFromBuffer(byte[] src, IntRef pointer) {
        super.copyFromBuffer(src, pointer);

        try {
            sdFileIdentifier.copyFromBuffer(src, pointer);
            sdFileType.copyFromBuffer(src, pointer);
            sdFileVersion.copyFromBuffer(src, pointer);
            sdModId.copyFromBuffer(src, pointer);
            sdDataLength.copyFromBuffer(src, pointer);
        } catch (Exception e) {
            HypercubeLib.LOG.error(e);
        }

        fileIdentifier = (String)sdFileIdentifier.deserialise();
        fileType = (byte)sdFileType.deserialise();
        fileVersion = (short)sdFileVersion.deserialise();
        modId = (String)sdModId.deserialise();
        dataLength = (long)sdDataLength.deserialise();

        return this;
    }

    @Override
    public int size() {
        return
                super.size() +
                sdFileIdentifier.size() +
                sdFileType.size() +
                sdFileVersion.size() +
                sdModId.size() +
                sdDataLength.size();
    }

    public String getFileIdentifier() {
        return this.fileIdentifier;
    }

    public byte getFileType() {
        return this.fileType;
    }

    public short getFileVersion() {
        return this.fileVersion;
    }

    public String getModId() {
        return this.modId;
    }

    public void setDataLength(long dataLength) {
        this.dataLength = dataLength;
        this.sdDataLength.serialize(dataLength);
    }

    public long getDataLength() {
        return this.dataLength;
    }
}
