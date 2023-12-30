#include "ByteBuffer.h"

class Packet {

public:
  int packetId;
  String data;

  // Konstruktor für das Packet
  Packet(int packetId)
    : packetId(packetId) {}

  // Virtuelle Funktion zum Schreiben des Packets in ein Byte-Array
  virtual void write(const ByteBuffer& buffer) const = 0;
  virtual void read(ByteBuffer buffer){};
  virtual int size() const = 0;

  long byteToLong(byte* byteArray) {
  return ((long)byteArray[0] << 56) | ((long)byteArray[1] << 48) | ((long)byteArray[2] << 40) | ((long)byteArray[3] << 32) |
         ((long)byteArray[4] << 24) | ((long)byteArray[5] << 16) | ((long)byteArray[6] << 8) | (long)byteArray[7];
  }
  void intToByteArray(int value, byte* byteArray) {
    byteArray[0] = (value >> 24) & 0xFF;
    byteArray[1] = (value >> 16) & 0xFF;
    byteArray[2] = (value >> 8) & 0xFF;
    byteArray[3] = value & 0xFF;
  }
};


class UUIDPacket : public Packet {
public:
  String uuid;


  UUIDPacket(const String& uuid)
    : Packet(1), uuid(uuid) {}

  void write(const ByteBuffer& buffer) const override {


    buffer.writeString(uuid);
  }
  void read(const ByteBuffer& buffer) override{

  }

  //Fixed length of 16  + 4
  int size() const override {
    return 4 + uuid.length();
  }
};

class DebugPacket : public Packet {
public:
  String debugMessage;


  DebugPacket(const String& debugMessage)
    : Packet(2), debugMessage(debugMessage) {}

  void write(const ByteBuffer& buffer) const override {
    buffer.writeString(debugMessage);
  }

  void read(const ByteBuffer& buffer) override{

  }

  //Fixed length of 16  + 4
  int size() const override {
    return 4 + debugMessage.length();
  }
};

class PingPacket : public Packet {
public:

  long stamp;

  PingPacket()
    : Packet(0) {}

  void write(const ByteBuffer& buffer) const override {
    buffer.writeLong(stamp);
  }
  void read(const ByteBuffer& buffer) override{
    stamp = buffer.readLong();
  }

  int size() const override {
    return 8;
  }
};


class ScenePacket : public Packet {
public:


  ScenePacket()
    : Packet(3) {}

  void write(const ByteBuffer& buffer) const override {

  }
  void read(const ByteBuffer& buffer) override{
  }

  //Fixed length of 16  + 4
  int size() const override {
    return 4;
  }
};