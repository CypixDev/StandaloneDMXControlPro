#include "Paket.h"
#include <EEPROM.h>

const int ledPin = 13;  // Pin für die LED
const int UUID_SIZE = 16;
const int UUID_ADDRESS = 0;

void setup() {
  pinMode(ledPin, OUTPUT);  // Setze den Pin-Modus auf Ausgang
  Serial.begin(9600);       // Starte die serielle Kommunikation mit einer Baudrate von 9600
  checkAndGenerateUUID();
}

void loop() {
  if (Serial.available() >= 4) {

    byte sizeBuffer[4];
    Serial.readBytes(sizeBuffer, 4);
    int packageSize = byteToInt(sizeBuffer);
    Serial.readBytes(sizeBuffer, 4);
    int packetId = byteToInt(sizeBuffer);

    switch (packetId) {
        case 1:
          sendHelloPacket();
            break;
        default:
          DebugPacket packet("Got not handeled packet id!");
          sendPacket(packet);
          break;
    }
  }
}

void sendHelloPacket() {
  char uuidBuffer[UUID_SIZE];
  readUUID(uuidBuffer);

  UUIDPacket uuidPacket(byteToString(uuidBuffer, sizeof(uuidBuffer)));
  sendPacket(uuidPacket);
}


void sendPacket(const Packet& packet) {
  int packetSize = 4 + 4 + static_cast<int>(packet.size());

  byte packetBuffer[packetSize];

  intToByteArray(packetSize, packetBuffer);
  intToByteArray(packet.packetId, packetBuffer + 4);

  packet.write(packetBuffer + 4 + 4);

  Serial.write(packetBuffer, packetSize);
  Serial.flush();
}

void checkAndGenerateUUID() {
  char uuidBuffer[UUID_SIZE];
  readUUID(uuidBuffer);

  bool isEmpty = true;
  for (int i = 0; i < UUID_SIZE; ++i) {
    if (uuidBuffer[i] != 0) {
      isEmpty = false;
      break;
    }
  }
  if (isEmpty) {
    generateRandomUUID(uuidBuffer);
    writeUUID(uuidBuffer);
    //Serial.println("Generated and saved a new UUID in EEPROM.");
  } else {
    //Serial.println("UUID already exists in EEPROM.");
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
void readUUID(char* uuidBuffer) {
  for (int i = 0; i < UUID_SIZE; ++i) {
    uuidBuffer[i] = EEPROM.read(UUID_ADDRESS + i);
  }
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