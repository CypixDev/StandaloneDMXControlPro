
class TableStep {
private:
  int pos;
  int fadeTime;
  int holdTime;
  int channelValues[512];

public:
  TableStep() {}
  TableStep(int pos, int fadeTime, int holdTime, int inputChannelValues[])
    : pos(pos), fadeTime(fadeTime), holdTime(holdTime) {
    for (int i = 0; i < 512; ++i) {
      channelValues[i] = inputChannelValues[i];
    }
  }

  int getPos() {
    return pos;
  }

  int getFadeTime() {
    return fadeTime;
  }

  int getHoldTime() {
    return holdTime;
  }

  int* getChannelValues() {
    return channelValues;
  }
};

struct GroupColor {
  int r;
  int g;
  int b;

  GroupColor(int r, int g, int b) : r(r), g(g), b(b) {}
};

class MyScene {
private:
    String sceneUUID;
    String name;
    int time;

    GroupColor color;
    TableStep* steps;

public:
    int numberOfSteps;

    MyScene(String sceneUUID, String name, int time, GroupColor color, int numSteps)
    : sceneUUID(sceneUUID), name(name), time(time), color(color), numberOfSteps(numSteps)
    {
    }

    TableStep* getSteps() {
        return steps;
    }

    int getNumberOfSteps() {
        return numberOfSteps;
    }

    void initialize() {
        // Ihre Initialisierungslogik hier...
    }
};