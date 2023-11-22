class Packet {
public:
  int packetId;
  String data;

  // Konstruktor für das Packet
  Packet(int packetId)
    : packetId(packetId) {}

  // Virtuelle Funktion zum Schreiben des Packets in ein Byte-Array
  virtual void write(byte* buffer) const = 0;
  virtual void read(byte* buffer){};
  virtual int size() const = 0;

  void stringToByteArray(String str, byte* byteArray) {
    for (int i = 0; i < str.length(); i++) {
      byteArray[i] = str[i];
    }
  }

  int byteToInt(byte* byteArray) {
    return (byteArray[0] << 24) | (byteArray[1] << 16) | (byteArray[2] << 8) | byteArray[3];
  }
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

  void write(byte* buffer) const override {
    intToByteArray(uuid.length(), buffer);
    stringToByteArray(uuid, buffer+4);
  }
  void read(byte* buffer) override{

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

  void write(byte* buffer) const override {
    intToByteArray(debugMessage.length(), buffer);
    stringToByteArray(debugMessage, buffer+4);
  }

  void read(byte* buffer) override{

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

  void write(byte* buffer) const override {
    intToByteArray(stamp, buffer);
  }
  void read(byte* buffer) override{
    stamp = byteToLong(buffer);
  }

  int size() const override {
    return 8;
  }
};