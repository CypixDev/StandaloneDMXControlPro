const int ledPin = 13; // Pin für die LED

void setup() {
  pinMode(ledPin, OUTPUT); // Setze den Pin-Modus auf Ausgang
  Serial.begin(9600); // Starte die serielle Kommunikation mit einer Baudrate von 9600
}

void loop() {
  if (Serial.available() >= 4) {

    // Wenn mindestens 4 Bytes (ein Integer) verfügbar sind, lese sie in ein Array
    byte sizeBuffer[4];
    Serial.readBytes(sizeBuffer, 4);


    // Konvertiere die Bytes in einen Integer (Little Endian)
    int packageSize = byteToInt(sizeBuffer);


    // Blinkvorgänge durchführen basierend auf dem empfangenen Integer
    for (int i = 0; i < packageSize; i++) {
      digitalWrite(ledPin, HIGH); // Schalte die LED ein
      delay(500); // Warte 500 Millisekunden
      digitalWrite(ledPin, LOW); // Schalte die LED aus
      delay(500); // Warte weitere 500 Millisekunden
    }

    /*byte arr[4];
    intToByteArray(4, arr);
    Serial.write(arr, 4);
    intToByteArray(1, arr);
    Serial.write(arr, 4);
    Serial.flush();*/
    sendPacket(1, "hello dude");
    Serial.flush();
  }
}

void sendPacket(int packetId, String data) {
  // Berechne die Größe des Pakets
  int packetSize = 4 + 4 + 4 + static_cast<int>(data.length());;

  // Erstelle ein Byte-Array für das Paket
  byte packet[packetSize];

  // Schreibe die Werte ins Byte-Array
  intToByteArray(packetSize, packet);
  intToByteArray(packetId, packet + 4);
  intToByteArray(data.length(), packet + 2 * 4);
  stringToByteArray(data, packet + 3 * 4);

  // Sende das Paket über die serielle Schnittstelle
  Serial.write(packet, packetSize);
  Serial.flush();
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