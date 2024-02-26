#ifndef SCENE_H
#define SCENE_H

#include <ArduinoJson.h>

class TableStep {

public:
  int pos;
  int fadeTime;
  int holdTime;
  byte channelValues[20];

  TableStep() {}
  TableStep(int pos, int fadeTime, int holdTime, byte inputChannelValues[])
    : pos(pos), fadeTime(fadeTime), holdTime(holdTime) {
    for (int i = 0; i < sizeof(channelValues); ++i) {
      channelValues[i] = inputChannelValues[i];
    }
  }
};

struct GroupColor {
  int r;
  int g;
  int b;

  GroupColor(int r, int g, int b)
    : r(r), g(g), b(b) {}
};

class MyScene {
public:
  String sceneUUID;
  String name;
  int time;

  GroupColor color;
  TableStep* steps;

  int numberOfSteps;
  MyScene();

  MyScene(String sceneUUID, String name, int time, GroupColor color, TableStep* inputSteps, int numSteps)
    : sceneUUID(sceneUUID), name(name), time(time), color(color), steps(inputSteps), numberOfSteps(numSteps) {
  }


  void initialize() {
    // Ihre Initialisierungslogik hier...
  }
  String toJSONString() {
    // Erstellen eines dynamischen JSON-Dokuments
    DynamicJsonDocument doc(1024);  // Passen Sie die Größe nach Bedarf an

    // Füllen des Dokuments mit Szene-Informationen
    doc["sceneUUID"] = sceneUUID;
    doc["name"] = name;
    doc["time"] = time;
    JsonObject colorObj = doc.createNestedObject("color");
    colorObj["r"] = color.r;
    colorObj["g"] = color.g;
    colorObj["b"] = color.b;
    doc["numberOfSteps"] = numberOfSteps;

    JsonArray stepsArray = doc.createNestedArray("steps");
    for (int i = 0; i < numberOfSteps; i++) {
      JsonObject stepObj = stepsArray.createNestedObject();
      stepObj["pos"] = steps[i].pos;
      stepObj["fadeTime"] = steps[i].fadeTime;
      stepObj["holdTime"] = steps[i].holdTime;
      JsonArray channelValuesArray = stepObj.createNestedArray("channelValues");
      for (int j = 0; j < 20; j++) {  // Angenommen, es gibt 20 Kanalwerte pro Schritt
        channelValuesArray.add(steps[i].channelValues[j]);
      }
    }

    // Konvertieren des JSON-Dokuments in einen String
    String output;
    serializeJson(doc, output);
    return output;
  }
  static MyScene fromJSONString(const String& jsonString) {
    // Erstellen eines temporären Szene-Objekts
    MyScene scene;

    // Erstellen eines dynamischen JSON-Dokuments für das Parsen
    DynamicJsonDocument doc(1024);  // Passen Sie die Größe nach Bedarf an

    // Parsen des JSON-Strings
    auto error = deserializeJson(doc, jsonString);
    if (error) {
      // Fehlerbehandlung, falls das Parsen fehlschlägt
      Serial.print(F("deserializeJson() failed: "));
      Serial.println(error.f_str());
      return scene;  // Zurückgeben einer leeren Szene oder Fehlerbehandlung
    }

    // Extrahieren und Zuweisen der Szene-Informationen aus dem JSON-Dokument
    scene.sceneUUID = doc["sceneUUID"].as<String>();
    scene.name = doc["name"].as<String>();
    scene.time = doc["time"].as<unsigned long>();
    JsonObject colorObj = doc["color"];
    scene.color.r = colorObj["r"].as<int>();
    scene.color.g = colorObj["g"].as<int>();
    scene.color.b = colorObj["b"].as<int>();
    scene.numberOfSteps = doc["numberOfSteps"].as<int>();

    JsonArray stepsArray = doc["steps"];
    for (JsonArray::iterator it = stepsArray.begin(); it != stepsArray.end(); ++it) {
      JsonObject stepObj = it->as<JsonObject>();
      TableStep step;
      step.pos = stepObj["pos"].as<int>();
      step.fadeTime = stepObj["fadeTime"].as<int>();
      step.holdTime = stepObj["holdTime"].as<int>();
      JsonArray channelValuesArray = stepObj["channelValues"];
      for (int j = 0; j < channelValuesArray.size(); j++) {
        step.channelValues[j] = channelValuesArray[j].as<int>();  // Stellen Sie sicher, dass channelValues richtig initialisiert ist
      }
      // Hier müssen Sie die Logik hinzufügen, um den Schritt `step` zur Szene `scene` hinzuzufügen
      // Dies könnte das Hinzufügen zum `steps`-Array oder einer anderen Datenstruktur sein, je nachdem, wie Ihre Klasse organisiert ist.
    }

    // Zurückgeben des gefüllten Szene-Objekts
    return scene;
  }
};
#endif