#ifndef SCENE_H
#define SCENE_H


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

  MyScene(String sceneUUID, String name, int time, GroupColor color, TableStep* inputSteps, int numSteps)
    : sceneUUID(sceneUUID), name(name), time(time), color(color), steps(inputSteps), numberOfSteps(numSteps) {
  }


  void initialize() {
    // Ihre Initialisierungslogik hier...
  }
};
#endif