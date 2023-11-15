class Packet {
public:
  int packetId;
  String data;

  // Konstruktor für das Packet
  Packet(int packetId)
    : packetId(packetId) {}

  // Virtuelle Funktion zum Schreiben des Packets in ein Byte-Array
  virtual void write(byte* buffer) const = 0;
  virtual int size() const = 0;

  void stringToByteArray(String str, byte* byteArray) {
    for (int i = 0; i < str.length(); i++) {
      byteArray[i] = str[i];
    }
  }

  int byteToInt(byte* byteArray) {
    return (byteArray[0] << 24) | (byteArray[1] << 16) | (byteArray[2] << 8) | byteArray[3];
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

  //Fixed length of 16  + 4
  int size() const override {
    return 4 + uuid.length();
  }
};