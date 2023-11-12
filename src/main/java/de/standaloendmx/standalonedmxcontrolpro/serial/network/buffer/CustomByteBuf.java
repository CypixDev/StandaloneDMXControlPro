package de.standaloendmx.standalonedmxcontrolpro.serial.network.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class CustomByteBuf {

    private ByteBuffer buffer;

    public CustomByteBuf() {
        this(16); // Initialisierung mit einer kleinen Kapazität
    }

    public CustomByteBuf(int initialCapacity) {
        buffer = ByteBuffer.allocate(initialCapacity);
    }

    public CustomByteBuf order(ByteOrder order) {
        buffer.order(order);
        return this;
    }

    public CustomByteBuf clear() {
        buffer.clear();
        return this;
    }

    public CustomByteBuf readerIndex(int index) {
        buffer.position(index);
        return this;
    }

    public int readerIndex() {
        return buffer.position();
    }

    public CustomByteBuf writerIndex(int index) {
        buffer.position(index);
        return this;
    }

    public int writerIndex() {
        return buffer.position();
    }

    public CustomByteBuf markReaderIndex() {
        buffer.mark();
        return this;
    }

    public CustomByteBuf resetReaderIndex() {
        buffer.reset();
        return this;
    }

    public CustomByteBuf markWriterIndex() {
        buffer.mark();
        return this;
    }

    public CustomByteBuf resetWriterIndex() {
        buffer.reset();
        return this;
    }

    public CustomByteBuf skipBytes(int length) {
        buffer.position(buffer.position() + length);
        return this;
    }

    public CustomByteBuf writeByte(byte value) {
        ensureWritable(1);
        buffer.put(value);
        return this;
    }

    public CustomByteBuf writeShort(short value) {
        ensureWritable(2);
        buffer.putShort(value);
        return this;
    }

    public CustomByteBuf writeInt(int value) {
        ensureWritable(4);
        buffer.putInt(value);
        return this;
    }

    public CustomByteBuf writeLong(long value) {
        ensureWritable(8);
        buffer.putLong(value);
        return this;
    }

    public CustomByteBuf writeFloat(float value) {
        ensureWritable(4);
        buffer.putFloat(value);
        return this;
    }

    public CustomByteBuf writeDouble(double value) {
        ensureWritable(8);
        buffer.putDouble(value);
        return this;
    }

    public CustomByteBuf writeBytes(CustomByteBuf src) {
        int readableBytes = src.readableBytes();
        ensureWritable(readableBytes);
        buffer.put(src.buffer);
        return this;
    }
    public CustomByteBuf writeBytes(byte[] bytes) {
        ensureWritable(bytes.length);
        buffer.put(bytes);
        return this;
    }

    public CustomByteBuf readBytes(int length) {
        if (readableBytes() < length) {
            throw new IndexOutOfBoundsException("Not enough readable bytes");
        }

        byte[] bytes = new byte[length];
        buffer.get(bytes);
        return new CustomByteBuf(bytes.length).order(buffer.order());
    }

    public byte readByte() {
        return buffer.get();
    }

    public short readShort() {
        return buffer.getShort();
    }

    public int readInt() {
        return buffer.getInt();
    }

    public long readLong() {
        return buffer.getLong();
    }

    public float readFloat() {
        return buffer.getFloat();
    }

    public double readDouble() {
        return buffer.getDouble();
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
        return buffer.capacity() - buffer.position();
    }

    public CustomByteBuf writeString(String value) {
        byte[] stringBytes = value.getBytes();
        writeInt(stringBytes.length);
        writeBytes(stringBytes);
        return this;
    }


    private void ensureWritable(int size) {
        if (writableBytes() < size) {
            int newCapacity = Math.max(buffer.capacity() << 1, buffer.capacity() + size);
            ByteBuffer newBuffer = ByteBuffer.allocate(newCapacity).order(buffer.order());
            buffer.flip();
            newBuffer.put(buffer);
            buffer = newBuffer;
        }
    }

}
