package de.standaloendmx.standalonedmxcontrolpro.serial.network.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class CustomByteBuf {

    private ByteBuffer buffer;

    private int readerIndex;
    private int readerMark;
    private int writerIndex;
    private int writerMark;

    public CustomByteBuf() {
        this(8); // Initialisierung mit einer kleinen Kapazität
    }

    public CustomByteBuf(int initialCapacity) {
        readerIndex = 0;
        writerIndex = 0;
        buffer = ByteBuffer.allocate(initialCapacity);
    }

    public CustomByteBuf(byte[] bytes) {
        this(bytes.length);
        writeBytes(bytes);
    }


    public CustomByteBuf order(ByteOrder order) {
        buffer.order(order);
        return this;
    }

    public CustomByteBuf clear() {
        buffer.clear();
        resetPositions();
        return this;
    }

    /**
     * Setting the current reader position
     *
     * @param index
     * @return
     */

    public CustomByteBuf readerIndex(int index) {
        readerIndex = index;
        return this;
    }

    public int readerIndex() {
        return readerIndex;
    }

    /**
     * Setting current writer position
     *
     * @param index
     * @return
     */

    public CustomByteBuf writerIndex(int index) {
        writerIndex = index;
        return this;
    }

    public int writerIndex() {
        return writerIndex;
    }

    public CustomByteBuf markReaderIndex() {
        readerMark = readerIndex;
        return this;
    }

    public CustomByteBuf resetReaderIndex() {
        readerIndex = 0;
        return this;
    }

    public CustomByteBuf markWriterIndex() {
        writerMark = writerIndex;
        return this;
    }

    public CustomByteBuf resetWriterIndex() {
        writerIndex = 0;
        return this;
    }

    /**
     * Increases the reader index by length
     *
     * @param length
     * @return
     */
    public CustomByteBuf skipBytes(int length) {
        readerIndex(readerIndex + length);
        return this;
    }

    public CustomByteBuf writeByte(byte value) {
        ensureWritable(1);
        buffer.position(writerIndex());
        buffer.put(value);
        writerIndex++;
        return this;
    }

    public CustomByteBuf writeShort(short value) {
        ensureWritable(2);
        buffer.position(writerIndex);
        buffer.putShort(value);
        writerIndex += 2;
        return this;
    }

    public CustomByteBuf writeInt(int value) {
        ensureWritable(4);
        buffer.position(writerIndex);
        buffer.putInt(value);
        writerIndex += 4;
        return this;
    }

    public CustomByteBuf writeLong(long value) {
        ensureWritable(8);
        buffer.position(writerIndex);
        buffer.putLong(value);
        writerIndex += 8;
        return this;
    }

    public CustomByteBuf writeFloat(float value) {
        ensureWritable(4);
        buffer.position(writerIndex);
        buffer.putFloat(value);
        writerIndex += 4;
        return this;
    }

    public CustomByteBuf writeDouble(double value) {
        ensureWritable(8);
        buffer.position(writerIndex);
        buffer.putDouble(value);
        writerIndex += 8;
        return this;
    }

    public CustomByteBuf writeBytes(byte[] bytes) {
        ensureWritable(bytes.length);
        buffer.position(writerIndex);
        buffer.put(bytes);
        writerIndex += bytes.length;
        return this;
    }

    /**
     * Reading bytes from readerIndex
     *
     * @param length
     * @return
     */
    public byte[] readBytes(int length) {
        byte[] bytes = new byte[length];

        for (int i = 0; i < length; i++) {
            bytes[i] = readByte();
        }
        return bytes;
    }

    public String readString() {
        int length = readInt();
        byte[] stringBytes = readBytes(length);
        readerIndex += length + 4;
        System.out.println();
        return new String(stringBytes, StandardCharsets.UTF_8);
    }

    public CustomByteBuf writeSizePrefixedBytes() {
        ensureWritable(4);
        // Holen Sie die Länge des Puffers
        int size = writerIndex;
        System.out.println("Out size: "+size);


        // Sichern Sie den aktuellen Inhalt des Puffers
        byte[] content = new byte[size];
        buffer.position(0);

        buffer.get(content);
        writerIndex = 0;
        ensureWritable(size);

        writeInt(size);
        writeBytes(content);
        //buffer.put(content);
        return this;
    }

    public byte readByte() {
        byte tmp = buffer.get(readerIndex);
        readerIndex++;
        return tmp;
    }

    public short readShort() {
        short tmp = buffer.getShort(readerIndex);
        readerIndex *= 2;
        return tmp;
    }

    public int readInt() {
        int tmp = buffer.getInt(readerIndex);
        readerIndex += 4;
        return tmp;
    }

    public long readLong() {
        long tmp = buffer.getLong(readerIndex);
        readerIndex += 8;
        return tmp;
    }

    public float readFloat() {
        float tmp = buffer.getFloat(readerIndex);
        readerIndex += 4;
        return tmp;
    }

    public double readDouble() {
        double tmp = buffer.getDouble(readerIndex);
        readerIndex += 8;
        return tmp;
    }

    public byte[] array() {
        return buffer.array();
    }

    public int capacity() {
        return buffer.capacity();
    }

    public int readableBytes() {
        return buffer.remaining();
    }

    public int writableBytes() {
        return buffer.capacity() - writerIndex;
    }

    public CustomByteBuf writeString(String value) {
        byte[] stringBytes = value.getBytes();
        writeInt(stringBytes.length);
        writeBytes(stringBytes);
        return this;
    }


    private void ensureWritable(int size) {
        if (writableBytes() < size) {
            int newCapacity = (buffer.capacity() + (size - writableBytes()));
            ByteBuffer newBuffer = ByteBuffer.allocate(newCapacity).order(buffer.order());
            buffer.flip();
            newBuffer.put(buffer);
            buffer = newBuffer;
        }
    }

    public void printInConsole() {
        System.out.print("CustomByteBuf Contents: ");
        System.out.println();
        for (byte b : buffer.array()) {
            System.out.print(b + " ");
        }
        System.out.println();
        for (byte b : buffer.array()) {
            int unsignedByteValue = b & 0xFF;
            System.out.print(unsignedByteValue + " ");
        }
        System.out.println(); // Neue Zeile für bessere Lesbarkeit
    }


    public void resetPositions() {
        readerIndex = 0;
        writerIndex = 0;
    }
}
