#include "Paket.h"
#include <EEPROM.h>
#include "ByteBuffer.h"

#define SERIAL_TX_BUFFER_SIZE 512
#define SERIAL_RX_BUFFER_SIZE 512


const int ledPin = 13;  // Pin für die LED
const int UUID_SIZE = 16;
const int UUID_ADDRESS = 0;


void setup() {
  char uuidBuffer[UUID_SIZE];
  generateRandomUUID(uuidBuffer);


  pinMode(ledPin, OUTPUT);  // Setze den Pin-Modus auf Ausgang
  Serial.begin(9600);       // Starte die serielle Kommunikation mit einer Baudrate von 9600
  checkAndGenerateUUID();
}

void loop() {
  while (Serial.available() < 8) {
    delay(10);
  }

  ByteBuffer byteBuffer;

  byte buffer[8];
  Serial.readBytes(buffer, 8);

  int packageSize = byteToInt(buffer);
  int packetId = byteToInt(buffer + 4);

  byteBuffer.readToByteBuffer(packageSize - 4);
  blink(2);

  if (packetId == 0) {
    PingPacket pingPacket;
    //pingPacket.read(packetBytes);
    sendPacket(pingPacket);
  } else if (packetId == 1) {
    sendHelloPacket();
  } else if (packetId == 2) {

  } else if (packetId == 3) {
    ScenePacket packet;
    packet.read(byteBuffer);

    DebugPacket debugPacket(String(packet.scene->getNumberOfSteps()));
    sendPacket(debugPacket);
  } else {
    DebugPacket debugPacket("Got not handeled packet id!");
    sendPacket(debugPacket);
  }
}


void blink(int c) {
  for (int i = 0; i < c; i++) {
    digitalWrite(ledPin, HIGH);
    delay(250);
    digitalWrite(ledPin, LOW);
    delay(250);
  }
}

void sendHelloPacket() {
  String uuid = readUUID();

  UUIDPacket uuidPacket(uuid);
  sendPacket(uuidPacket);
}


void sendPacket(const Packet& packet) {
  int packetSize = 4 + 4 + static_cast<int>(packet.size());

  ByteBuffer buf;

  buf.writeInt(packetSize);
  buf.writeInt(packet.packetId);

  packet.write(buf);
  //buf.writeString(readUUID());


  Serial.write(buf.getBuffer(), packetSize);
  Serial.flush();
}

void checkAndGenerateUUID() {
  String uuidStr = readUUID();

  bool isEmpty = false;

  for (int i = 0; i < UUID_SIZE; ++i) {
    if (EEPROM.read(i) == 255) {
      isEmpty = true;
      break;
    }
  }

  if (isEmpty) {
    char uuidBuffer[UUID_SIZE];
    generateRandomUUID(uuidBuffer);
    writeUUID(uuidBuffer);
  }
}
void generateRandomUUID(char* uuidBuffer) {
  for (int i = 0; i < UUID_SIZE; ++i) {
    // Generiere eine zufällige Zahl zwischen 0 und 255 (ein Byte)
    int randomValue = random(256);

    // Konvertiere die zufällige Zahl in ein Hexadezimalzeichen
    sprintf(uuidBuffer + i, "%02X", randomValue);
  }
}
String readUUID() {
  String uuidString;
  for (int i = 0; i < UUID_SIZE; ++i) {
    uuidString += char(EEPROM.read(UUID_ADDRESS + i));
  }
  return uuidString;
}
// Funktion zum Schreiben einer UUID in den EEPROM
void writeUUID(const char* uuidBuffer) {
  for (int i = 0; i < UUID_SIZE; ++i) {
    EEPROM.write(UUID_ADDRESS + i, uuidBuffer[i]);
  }
}


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
String byteToString(const byte* buffer, size_t size) {
  // Die Funktion String() interpretiert den Inhalt des Byte-Arrays als Null-terminierten ASCII-String
  return String(reinterpret_cast<const char*>(buffer));
}