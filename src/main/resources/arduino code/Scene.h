#ifndef SCENE_H
#define SCENE_H

#include <ArduinoJson.h>

struct ChannelValue {
  short channel;
  byte value;
};

class TableStep {

public:
  int pos;
  int fadeTime;
  int holdTime;
  ChannelValue* channelValues;
  int channelCount;

  TableStep() {}
  TableStep(int pos, int fadeTime, int holdTime, ChannelValue* inputChannelValues, int inputChannelCount)
    : pos(pos), fadeTime(fadeTime), holdTime(holdTime), channelCount(inputChannelCount) {
    channelValues = new ChannelValue[channelCount];
    for (int i = 0; i < channelCount; ++i) {
      channelValues[i].channel = inputChannelValues[i].channel;
      channelValues[i].value = inputChannelValues[i].value;
    }
  }
};

struct GroupColor {
  byte r, g, b;

  GroupColor(byte r, byte g, byte b)
    : r(r), g(g), b(b) {}
};

String splitString(String data, char separator, int index) {
  int found = 0;
  int strIndex[] = { 0, -1 };
  int maxIndex = data.length() - 1;

  for (int i = 0; i <= maxIndex && found <= index; i++) {
    if (data.charAt(i) == separator || i == maxIndex) {
      found++;
      strIndex[0] = strIndex[1] + 1;
      strIndex[1] = (i == maxIndex) ? i + 1 : i;
    }
  }

  return found > index ? data.substring(strIndex[0], strIndex[1]) : "";
}

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
      for (int j = 0; j < steps[i].channelCount; j++) {
        channelValuesArray.add(String(steps[i].channelValues[j].channel) + ":" + String(steps[i].channelValues[j].value));
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
        String channelValuePair = channelValuesArray[j].as<String>();
        String channelStr = splitString(channelValuePair, ':', 0);
        String valueStr = splitString(channelValuePair, ':', 1);

        ChannelValue channelValue;
        channelValue.channel = channelStr.toInt();
        channelValue.value = valueStr.toInt();
        step.channelValues[j] = channelValue;  // Angenommen, Sie haben Speicher für channelValues in 'step' reserviert
      }
      // Hier müssen Sie die Logik hinzufügen, um den Schritt `step` zur Szene `scene` hinzuzufügen
      // Dies könnte das Hinzufügen zum `steps`-Array oder einer anderen Datenstruktur sein, je nachdem, wie Ihre Klasse organisiert ist.
    }

    // Zurückgeben des gefüllten Szene-Objekts
    return scene;
  }
};
#endif