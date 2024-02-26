#include "SPI.h"
#include "FS.h"
#include <SD.h>
#include "Scene.h"
#include <LiquidCrystal_I2C.h>


class FileManager {
private:
  int pin;
  LiquidCrystal_I2C* lcd;

public:
  FileManager(int csPin, LiquidCrystal_I2C* lcdDisplay)
    : pin(csPin), lcd(lcdDisplay) {
    if (!SD.begin()) {
      lcd->print("Fehler bei init!");
      delay(2000);
      lcd->clear();
      //Serial.println("SD-Karteninitialisierung fehlgeschlagen!");
    }
  }

  // Methode zum Serialisieren von Scene
  String serializeMyScene(MyScene &scene) {
    String serializedString = "";

    serializedString += scene.sceneUUID + "\n";
    serializedString += scene.name + "\n";
    serializedString += String(scene.time) + "\n";
    serializedString += String(scene.color.r) + "\n";
    serializedString += String(scene.color.g) + "\n";
    serializedString += String(scene.color.b) + "\n";
    serializedString += String(scene.numberOfSteps) + "\n";

    for (int i = 0; i < scene.numberOfSteps; i++) {
      serializedString += serializeTableStep(scene.steps[i]);
    }

    return serializedString;
  }

  // Methode zum Deserialisieren von Scene
  MyScene *deserializeMyScene(String data) {
    int startPos, endPos;

    endPos = data.indexOf('\n');
    String sceneUUID = data.substring(0, endPos);

    startPos = endPos + 1;
    endPos = data.indexOf('\n', startPos);
    String name = data.substring(startPos, endPos);

    startPos = endPos + 1;
    endPos = data.indexOf('\n', startPos);
    int time = data.substring(startPos, endPos).toInt();

    startPos = endPos + 1;
    endPos = data.indexOf('\n', startPos);
    int color_r = data.substring(startPos, endPos).toInt();

    startPos = endPos + 1;
    endPos = data.indexOf('\n', startPos);
    int color_g = data.substring(startPos, endPos).toInt();

    startPos = endPos + 1;
    endPos = data.indexOf('\n', startPos);
    int color_b = data.substring(startPos, endPos).toInt();

    GroupColor color(color_r, color_g, color_b);

    startPos = endPos + 1;
    endPos = data.indexOf('\n', startPos);
    int numberOfSteps = data.substring(startPos, endPos).toInt();

    TableStep *steps = new TableStep[numberOfSteps];

    for (int i = 0; i < numberOfSteps; i++) {
      startPos = endPos + 1;
      steps[i] = deserializeTableStep(data, startPos);
      endPos = data.indexOf('\n', startPos);
    }
    return new MyScene(sceneUUID, name, time, color, steps, numberOfSteps);
  }
  TableStep deserializeTableStep(String &data, int &pos) {
    int endPos;

    endPos = data.indexOf('\n', pos);
    int tablePos = data.substring(pos, endPos).toInt();

    pos = endPos + 1;
    endPos = data.indexOf('\n', pos);
    int fadeTime = data.substring(pos, endPos).toInt();

    pos = endPos + 1;
    endPos = data.indexOf('\n', pos);
    int holdTime = data.substring(pos, endPos).toInt();

    byte *channelValues = new byte[20];
    for (int i = 0; i < 20; ++i) {
      pos = endPos + 1;
      endPos = data.indexOf('\n', pos);
      channelValues[i] = (byte)data.substring(pos, endPos).toInt();
    }

    pos = endPos + 1;

    return TableStep(tablePos, fadeTime, holdTime, channelValues);
  }
  // Methode zum Serialisieren von TableStep
  String serializeTableStep(TableStep &step) {
    String serializedString = "";

    serializedString += String(step.pos) + "\n";
    serializedString += String(step.fadeTime) + "\n";
    serializedString += String(step.holdTime) + "\n";

    byte *channelValues = step.channelValues;
    for (int i = 0; i < 20; ++i) {
      serializedString += String(channelValues[i]) + "\n";
    }

    return serializedString;
  }

  void saveScene(String filename, MyScene *scene) {
    File file = SD.open(filename, FILE_WRITE);
    if (!file) {
      lcd->clear();
      lcd->print(SD.cardSize());
      //lcd->print("File could not open");
      delay(2000);
      lcd->clear();
      //Serial.println("Speichern der Szene fehlgeschlagen: konnte die Datei nicht öffnen.");
      return;
    }

    String sceneData = /*serializeMyScene(*scene);*/ scene->toJSONString();
    lcd->print(sceneData);
    delay(2000);
    /*for (int i = 0; i < sceneData.length(); i++) {
      file.write(sceneData[i]);
    }*/
    file.print(sceneData);

    file.close();
  }

  MyScene *loadScene(String filename) {
    File file = SD.open(filename);
    if (!file) {
      //Serial.println("Laden der Szene fehlgeschlagen: konnte die Datei nicht öffnen.");
      return nullptr;
    }

    // Erstellen Sie ein neues MyScene-Objekt und füllen Sie es mit Daten aus der Datei.
    MyScene *scene = deserializeMyScene(file.readString());

    file.close();

    return scene;
  }
};